package uz.chatserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.chatserver.model.Chat;
import uz.chatserver.model.Message;
import uz.chatserver.model.MessageType;
import uz.chatserver.service.AwsService;
import uz.chatserver.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;
    private final AwsService awsService;

    public MessageController(MessageService messageService, AwsService awsService) {
        this.messageService = messageService;
        this.awsService = awsService;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileToS3(@RequestParam("file") MultipartFile file) {
        try {
            // Upload the file to AWS S3
            String fileUrl = awsService.uploadFile(file);

            // Create a new message with the file URL
            Message message = new Message();
            message.setContentType(MessageType.IMAGE);
            message.setContent(fileUrl);

            // Save the message
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
