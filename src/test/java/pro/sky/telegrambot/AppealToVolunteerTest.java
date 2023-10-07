package pro.sky.telegrambot;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.controller.AppealToVolunteerController;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.AppealToVolunteerService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class AppealToVolunteerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppealToVolunteerService appealToVolunteerService;

    @InjectMocks
    private AppealToVolunteerController appealToVolunteerController;

    @Test
    public void saveAppeal() throws Exception {
        JsonObject appealObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;


        AppealToVolonteer appealToVolonteer = new AppealToVolonteer();
        AppealToVolonteer appealToVolonteer1 = new AppealToVolonteer();
        appealToVolonteer.setId(id);
        appealToVolonteer.setUser(user);
        appealToVolonteer1.setId(id1);
        appealToVolonteer1.setUser(user1);
        Collection<AppealToVolonteer> appeals = new ArrayList<>();
        appeals.add(appealToVolonteer);
        appeals.add(appealToVolonteer1);


        when(appealToVolunteerService.saveAppeal(any(AppealToVolonteer.class))).thenReturn(appealToVolonteer);
        when(appealToVolunteerService.getAllAppeals()).thenReturn(appeals);

        mockMvc.perform(MockMvcRequestBuilders
                    .post("/appeal")
                    .content(appealObject.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.user").value(user));
    }

    @Test
    public  void getAllAppealsTest() throws Exception{
        JsonObject appealObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;


        AppealToVolonteer appealToVolonteer = new AppealToVolonteer();
        AppealToVolonteer appealToVolonteer1 = new AppealToVolonteer();
        appealToVolonteer.setId(id);
        appealToVolonteer.setUser(user);
        appealToVolonteer1.setId(id1);
        appealToVolonteer1.setUser(user1);
        Collection<AppealToVolonteer> appeals = new ArrayList<>();

        appeals.add(appealToVolonteer);
        appeals.add(appealToVolonteer1);


        when(appealToVolunteerService.saveAppeal(any(AppealToVolonteer.class))).thenReturn(appealToVolonteer);
        when(appealToVolunteerService.getAllAppeals()).thenReturn(appeals);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/appeal")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appeals").value(appeals));

    }
}
