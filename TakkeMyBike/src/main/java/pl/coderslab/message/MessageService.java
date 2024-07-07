package pl.coderslab.message;

import org.springframework.stereotype.Service;
import pl.coderslab.utils.Crypt;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    public void save(Message message) {
        messageRepository.save(message);
    }
    public List <Message> getMessagesBetweenUsers (Long id1, Long id2) {
        return messageRepository.findAllByInterlocutors(id1, id2);
    }
    public void decryptAndEncryptAgainWithNewPassword (Long id, String oldPassword) {
        List <Message> messages = messageRepository.findAllByUser(id);
        messages
                .stream()
                .map(s -> {
                    String senderpsw = s.getSender().getPassword();
                    String rcvpsq = s.getReceiver().getPassword();
                    String oldpsw = oldPassword;
                    String content = s.content;
                    try {
                        if (s.getSender().getId().equals(id)){
                            s.setContent(Crypt.decrypt(s.getContent(), oldPassword, s.getReceiver().getPassword()));
                        } else {
                            s.setContent(Crypt.decrypt(s.getContent(), s.getSender().getPassword(), oldPassword));
                        }
                        return s;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(s -> {
                    try {
                        s.setContent(Crypt.encrypt(s.getContent(), s.getSender().getPassword(), s.getReceiver().getPassword()));
                        return s;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(this::save);
    }

    public List <Message> myMessages (Long id){
        return messageRepository.findAllBySenderIdOrReceiverId(id,id);
    }
}
