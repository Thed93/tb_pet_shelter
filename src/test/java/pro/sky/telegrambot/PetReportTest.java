package pro.sky.telegrambot;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.telegrambot.controller.PetReportController;
import pro.sky.telegrambot.service.PetReportService;

@WebMvcTest
public class PetReportTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetReportService petReportService;

    @InjectMocks
    private PetReportController petReportController;

    @Test
    public void savePetReportTest(){

    }
}
