package pl.coderslab.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.user.User;
import pl.coderslab.user.UserService;
import pl.coderslab.utils.Crypt;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/message")
@RequiredArgsConstructor

public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping
    public String messageCenter(HttpSession session, Model model){
        if (getLoggedUser(session, model)) return "redirect:/";
        return "Messages";
    }

    private boolean getLoggedUser(HttpSession session, Model model) {
        Long id = Long.valueOf(session.getAttribute("id").toString());
        Optional<User> loggedUserOpt = userService.findById(id);
        if (loggedUserOpt.isEmpty()){
            return true;
        }
        User loggedUser = loggedUserOpt.get();
        getMyInterlocutors(model,loggedUser);
        loggedUser.setHasMessageNotification(false);
        userService.save(loggedUser);
        session.setAttribute("hasMessageNotification", loggedUser.isHasMessageNotification());
        return false;
    }

    @GetMapping("/user/{receiverId}")
    public String get (@PathVariable Long receiverId, Model model, HttpSession session) {
        if (getLoggedUser(session, model)) return "redirect:/";
        Message message = new Message();
        Long senderId = Long.valueOf(session.getAttribute("id").toString());
        Optional<User> senderOpt = userService.findById(senderId);
        Optional<User> receiverOpt = userService.findById(receiverId);
        if (receiverOpt.isEmpty() || senderOpt.isEmpty()){
            return "redirect:/";
        }
        message.setReceiver(receiverOpt.get());
        message.setSender(senderOpt.get());
        List <Message> messages = messageService.getMessagesBetweenUsers(receiverId, senderId)
                .stream()
                .sorted(Comparator.comparing(Message::getSendTime))
                .map(s -> {
                    try {
                        s.setContent(Crypt.decrypt(s.getContent(), s.getSender().getPassword(), s.getReceiver().getPassword()));
                        return s;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        model.addAttribute("messages", messages);
        model.addAttribute("message", message);
        return "Messages";
    }
    @PostMapping("/send")
    public String post (@ModelAttribute Message message, @RequestParam("receiverId") Long receiverId, HttpSession session) throws Exception {
        Long senderId = Long.valueOf(session.getAttribute("id").toString());
        Optional<User> senderOpt = userService.findById(senderId);
        Optional<User> receiverOpt = userService.findById(receiverId);
        if (receiverOpt.isEmpty() || senderOpt.isEmpty()){
            return "redirect:/";
        }
        User sender = senderOpt.get();
        User receiver = receiverOpt.get();
        message.setSendTime(LocalDateTime.now());
        message.setContent(Crypt.encrypt(message.getContent(), sender.getPassword(), receiver.getPassword()));
        message.setReceiver(receiver);
        receiver.setHasMessageNotification(true);
        userService.save(receiver);
        message.setSender(sender);
        messageService.save(message);
        return "redirect:/message/user/"+receiverId;
    }

    private void getMyInterlocutors (Model model, User loggedUser){

        List <User> interlocutorsList = messageService.myMessages(loggedUser.getId())
                .stream()
                .sorted((s1,s2) -> s2.getSendTime().compareTo(s1.getSendTime()))
                .map(s -> {
                    if (Objects.equals(loggedUser.getId(), s.getSender().getId())) {
                        return s.getReceiver();
                    } else {
                        return s.getSender();
                    }
                })
                .distinct()
                .toList();
        model.addAttribute("interlocutors", interlocutorsList);
    }
}
