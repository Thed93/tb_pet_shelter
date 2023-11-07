package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.exception.UserNotFoundException;
import pro.sky.telegrambot.repository.UserChatRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * service for processing commands
 */
@Service
public class UserChatService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserChatService.class);

    /**
     * repository for data access
     */
    private final UserChatRepository userChatRepository;

    public UserChatService(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
    }

    /**
     *
     * save user in database
     * <br>
     * use repository method {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param user
     * @return saving user
     */
    public UserChat saveUser(UserChat user){
        if (user.getSurname() == null){
            user.setSurname("Иванов");
        }
        return userChatRepository.save(user);
    }

    public BotState getUserChatStatus(long id) {
        return Optional.ofNullable(userChatRepository.findStatusUserChatByUserId(id)).orElseThrow(UserNotFoundException::new);
    }

    /**
     *
     * get all useres, that use Telegram - bot
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all users from database
     */
    public Collection<UserChat> getAllUsers (){
        return userChatRepository.findAll();
    }

}