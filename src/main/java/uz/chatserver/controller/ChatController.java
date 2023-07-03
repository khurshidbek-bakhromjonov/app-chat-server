package uz.chatserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.chatserver.model.Chat;
import uz.chatserver.model.User;
import uz.chatserver.service.ChatService;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/add")
    public ResponseEntity<?> addChat(@RequestBody Chat chat) {
        try {
            Chat createdChat = chatService.addChat(chat);
            return ResponseEntity.ok(createdChat.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/get")
    public ResponseEntity<?> getChatsByUser(@RequestBody User user) {
        try {
            Long userId = user.getId();
            List<Chat> chats = chatService.getChatsByUser(userId);
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
