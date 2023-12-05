package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.AppealToVolunteer;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.AppealToVolunteerRepository;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AppealToVolunteerTest {

    @Mock
    private AppealToVolunteerRepository appealToVolunteerRepository;

    @InjectMocks
    private AppealToVolunteerService appealToVolunteerService;

    final UserChat user = new UserChat(123L, "Jack", "Sparrow", null, false, null);
    AppealToVolunteer appeal = new AppealToVolunteer(user);

    List<AppealToVolunteer> appeals = new ArrayList<>();


    @Test
    void save(){
        when(appealToVolunteerRepository.save(any(AppealToVolunteer.class))).thenReturn(appeal);
        appealToVolunteerService.saveAppeal(appeal);
        verify(appealToVolunteerRepository, Mockito.times(1)).save(appeal);
    }
    @Test
    void getAllTest() {
        when(appealToVolunteerRepository.findAll()).thenReturn(appeals);
        appealToVolunteerService.getAllAppeals();
        verify(appealToVolunteerRepository, Mockito.times(1)).findAll();
    }


}