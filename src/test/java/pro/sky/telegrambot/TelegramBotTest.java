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
import pro.sky.telegrambot.controller.TelegramBotController;
import pro.sky.telegrambot.service.TelegramBotService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import static org.mockito.Mockito.when;

@WebMvcTest
public class TelegramBotTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private TelegramBotService telegramBotService;

    @InjectMocks
    private TelegramBotController telegramBotController;

    @Test
    public void sendMessageTest() throws Exception {
        JsonObject appealObject = new JsonObject();
        final long id = 23;
        final String text = "text";

        when(telegramBotService.sendMessage((any(Long.class)),any(String.class))).thenReturn(text);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tg_bot")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
