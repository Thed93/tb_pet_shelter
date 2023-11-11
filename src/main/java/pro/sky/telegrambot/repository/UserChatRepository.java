package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;

/**
 * repository for data access to users
 */
public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    public  UserChat findUserByNameAndSurname(String name, String surname);
    @Query(value = "SELECT * FROM user WHERE chat_id = :chatId", nativeQuery = true)
    UserChat findUserByChatId(@Param("chatId") Long chatId);

    BotState findStatusUserChatByUserId(@Param("userId") long userId);

}