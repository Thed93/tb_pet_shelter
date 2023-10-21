package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.AppealToVolunteerRepository;
import pro.sky.telegrambot.service.*;
import static org.mockito.Mockito.verify;


@WebMvcTest
public class AppealToVolunteerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AppealToVolunteerRepository appealToVolunteerRepository;

    @MockBean
    private AppealToVolunteerService appealToVolunteerService;

    final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    AppealToVolonteer appeal = new AppealToVolonteer(user);

    @Test

    void save(){
        appealToVolunteerService.saveAppeal(appeal);
        verify(appealToVolunteerRepository).save(appeal);
    }


}
