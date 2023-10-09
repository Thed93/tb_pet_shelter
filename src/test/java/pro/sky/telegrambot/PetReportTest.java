package pro.sky.telegrambot;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PetReportTest {

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
    public  void getAllReports() throws Exception {
        JsonObject reportObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;
        final String text = "Все отлично";
        final String text1 = "Все очень плохо";

        PetReport petReport = new PetReport();
        petReport.setUser(user);
        petReport.setDateTime(LocalDateTime.now());
        petReport.setPhoto(null);
        petReport.setText(text);
        petReport.setChatId(id);
        PetReport petReport1 = new PetReport();
        petReport1.setUser(user1);
        petReport1.setDateTime(LocalDateTime.now());
        petReport1.setPhoto(null);
        petReport1.setText(text1);
        petReport1.setChatId(id1);
        Collection<PetReport> reports = new ArrayList<>();
        reports.add(petReport);
        reports.add(petReport1);

        when(petReportService.getAllReports()).thenReturn(reports);
        when(petReportService.getReportsByUserNameAndSurname(any(String.class),any(String.class))).thenReturn(reports);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/pet_report")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public  void getReportsByNameAndSurnameTest() throws Exception {
        JsonObject reportObject = new JsonObject();
        final User user = new User("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id = 23;
        final User user1 = new User("Ваня", "Пряник", ShelterType.DOG_SHELTER, true, BotState.START);
        final long id1 = 32;
        final String text = "Все отлично";
        final String text1 = "Все очень плохо";

        PetReport petReport = new PetReport();
        petReport.setUser(user);
        petReport.setDateTime(LocalDateTime.now());
        petReport.setPhoto(null);
        petReport.setText(text);
        petReport.setChatId(id);
        PetReport petReport1 = new PetReport();
        petReport1.setUser(user1);
        petReport1.setDateTime(LocalDateTime.now());
        petReport1.setPhoto(null);
        petReport1.setText(text1);
        petReport1.setChatId(id1);
        Collection<PetReport> reports = new ArrayList<>();
        reports.add(petReport);
        reports.add(petReport1);

        when(petReportService.getAllReports()).thenReturn(reports);
        when(petReportService.getReportsByUserNameAndSurname(any(String.class), any(String.class))).thenReturn(reports);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/reports/by_name_and_surname").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
