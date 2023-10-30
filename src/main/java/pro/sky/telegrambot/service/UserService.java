package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.UserRepository;

import java.util.Collection;
import java.util.List;

/**
 * service for processing commands
 */
@Service
public class UserService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     * repository for data access
     */
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User saveUser(User user){
        if (user.getSurname() == null){
            user.setSurname("Иванов");
        }
        return userRepository.save(user);
    }

    /**
     *
     * get all useres, that use Telegram - bot
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all users from database
     */
    public Collection<User> getAllUsers (){
        return userRepository.findAll();
    }

}
