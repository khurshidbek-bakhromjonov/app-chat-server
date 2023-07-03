package uz.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.chatserver.model.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(value = "SELECT c.* FROM chats c " +
            "INNER JOIN chat_user cu ON c.id = cu.chat_id " +
            "WHERE cu.user_id = :userId " +
            "ORDER BY (SELECT MAX(m.created_at) FROM messages m WHERE m.chat_id = c.id) DESC",
            nativeQuery = true)
    List<Chat> getChatsByUser(@Param("userId") Long userId);
}