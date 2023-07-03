package uz.chatserver.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.chatserver.model.Chat;
import uz.chatserver.model.Message;
import uz.chatserver.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.addMessage(message);
            return ResponseEntity.ok(createdMessage.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/get")
    public ResponseEntity<?> getMessagesByChat(@RequestBody Chat chat) {
        try {
            Long chatId = chat.getId();
            List<Message> messages = messageService.getMessagesByChat(chatId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // other message-related endpoints
}
