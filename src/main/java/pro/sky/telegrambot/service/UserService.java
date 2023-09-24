package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.repository.UserRepository;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(String userName, String userSurname){
        if (userSurname.equals(null)){
            userSurname.equals("Иванов");
        }
        User user = new User(userName, userSurname);
        userRepository.save(user);
    }

    public void saveUser(User user) {
    }
}
