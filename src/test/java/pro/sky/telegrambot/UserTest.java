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
import pro.sky.telegrambot.controller.UserController;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.*;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppealToVolunteerService appealToVolunteerService;

    @MockBean
    private HelpService helpService;

    @MockBean
    private PetReportService petReportService;

    @MockBean
    private TelegramBotService telegramBotService;

    @MockBean
    private UserService userService;


    @Test
    public void saveUserTest() throws Exception {
        JsonObject userObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        Collection<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/user")
                        .content(userObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.surname").value(user.getSurname()));
    }

    @Test
    public void getAllUsersTest() throws Exception {
        JsonObject userObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        Collection<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
