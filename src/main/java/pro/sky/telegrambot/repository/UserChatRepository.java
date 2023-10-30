package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.UserChat;

/**
 * repository for data access to users
 */
public interface UserChatRepository extends JpaRepository<UserChat, Long> {


   public UserChat findUserByNameAndSurname(String name, String surname);
}
