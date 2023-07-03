package uz.chatserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.chatserver.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    User findByUsername(String username);
}