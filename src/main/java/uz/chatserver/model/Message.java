package uz.chatserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private MessageType contentType;

    @Column(columnDefinition = "TEXT")
    @Size(max = 100 * 1024 * 1024, message = "Image size should not exceed 100MB")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Message() {
    }

    public Message(Chat chat, User author, MessageType contentType, String content, LocalDateTime createdAt) {
        this.chat = chat;
        this.author = author;
        this.contentType = contentType;
        this.content = content;
        this.createdAt = createdAt;
    }
}
