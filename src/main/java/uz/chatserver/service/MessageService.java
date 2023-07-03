package uz.chatserver.service;

import org.springframework.stereotype.Service;
import uz.chatserver.model.Message;
import uz.chatserver.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message addMessage(Message message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByChat(Long chatId) {
        return messageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
    }

    // other message-related methods
}
