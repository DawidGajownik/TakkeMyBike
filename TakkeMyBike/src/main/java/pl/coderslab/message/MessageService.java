package pl.coderslab.message;

import org.springframework.stereotype.Service;

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
    public List <Message> myMessages (Long id){
        return messageRepository.findAllBySenderIdOrReceiverId(id,id);
    }
}
