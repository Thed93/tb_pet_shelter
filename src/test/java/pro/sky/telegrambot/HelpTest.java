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
import pro.sky.telegrambot.controller.HelpController;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.HelpService;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest

public class HelpTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HelpService helpService;

    @InjectMocks
    private HelpController helpController;

    @Test
    public  void saveHelpTest() throws Exception {
        JsonObject helpObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;
        final String text = "Мало котов";
        final String text1 = "Мало псов";

        Help help = new Help();
        Help help1 = new Help();
        help.setId(id);
        help.setText(text);
        help.setUser(user);
        help1.setId(id1);
        help1.setText(text1);
        help1.setUser(user1);

        Collection<Help> helpCollection = new ArrayList<>();
        helpCollection.add(help);
        helpCollection.add(help1);

        when(helpService.saveHelpAppeal(any(Help.class))).thenReturn(help);
        when(helpService.getAllHelps()).thenReturn(helpCollection);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/help")
                        .content(helpObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.user").value(user))
                .andExpect(jsonPath("$.text").value(text));
    }

    @Test
    public void getAllHelpAppeals() throws Exception {
        JsonObject helpObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;
        final String text = "Мало котов";
        final String text1 = "Мало псов";

        Help help = new Help();
        Help help1 = new Help();
        help.setId(id);
        help.setText(text);
        help.setUser(user);
        help1.setId(id1);
        help1.setText(text1);
        help1.setUser(user1);

        Collection<Help> helpCollection = new ArrayList<>();
        helpCollection.add(help);
        helpCollection.add(help1);

        when(helpService.saveHelpAppeal(any(Help.class))).thenReturn(help);
        when(helpService.getAllHelps()).thenReturn(helpCollection);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/help")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.helpCollection").value(helpCollection));
    }

}



