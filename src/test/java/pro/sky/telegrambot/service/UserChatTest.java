package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.UserChatRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserChatTest {

    @Mock
    private UserChatRepository userChatRepository;

    @InjectMocks
    private UserChatService userChatService;

    final UserChat user = new UserChat("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    List<UserChat> users = new ArrayList<>();

    @Test
    void saveTest() {
        when(userChatRepository.save(user)).thenReturn(user);
        userChatService.saveUser(user);
        verify(userChatRepository, Mockito.times(1)).save(user);
    }

    @Test
    void getAllTest() {
        when(userChatRepository.findAll()).thenReturn(users);
        userChatService.getAllUsers();
        verify(userChatRepository, Mockito.times(1)).findAll();
    }
}
