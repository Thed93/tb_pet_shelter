package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;

import java.util.Optional;

/**
 * repository for data access to users
 */
public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    public  UserChat findUserByNameAndSurname(String name, String surname);

//    BotState findBotStateByUserId(@Param("userId") long userId);

    Optional<UserChat> findUserChatByUserId(Long id);

}