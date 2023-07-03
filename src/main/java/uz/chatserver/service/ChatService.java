package uz.chatserver.service;

import org.springframework.stereotype.Service;
import uz.chatserver.model.Chat;
import uz.chatserver.repository.ChatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
    
    public Chat addChat(Chat chat) {
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

    public List<Chat> getChatsByUser(Long userId) {
        return chatRepository.getChatsByUser(userId);
    }
    
    // other chat-related methods
}
