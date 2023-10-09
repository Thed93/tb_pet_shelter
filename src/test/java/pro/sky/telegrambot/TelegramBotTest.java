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
import pro.sky.telegrambot.service.*;


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
    public void sendMessageTest() throws Exception {

        final String text = "text";
        final long id = 123;

        when(telegramBotService.sendMessage(any(Long.class)), any(String.class)).thenReturn(id, text);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tg_bot")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
