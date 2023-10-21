package pro.sky.telegrambot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.UserRepository;
import pro.sky.telegrambot.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@WebMvcTest
public class UserTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    @Test
    void save() {
        when(userRepository.save(user)).thenReturn(user);
        userService.saveUser(user);
        verify(userRepository, Mockito.times(1)).save(any());
    }
}
