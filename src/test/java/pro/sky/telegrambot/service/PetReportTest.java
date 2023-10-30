package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PetReportTest {

    @Mock
    private PetReportRepository petReportRepository;

    @InjectMocks
    private PetReportService petReportService;

    final UserChat user = new UserChat("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    final PetReport petReport = new PetReport(user, LocalDateTime.now(), null, "Вск хорошо", 12L);

    final List<PetReport> petReports = new ArrayList<>();
    @Test
    void save(){
        when(petReportRepository.save(any(PetReport.class))).thenReturn(petReport);
        petReportRepository.save(petReport);
        verify(petReportRepository, Mockito.times(1)).save(petReport);
    }

    @Test
    void findBy(){
        when(petReportRepository.findReportsByNameAndSurname((any(String.class)),(any(String.class)))).thenReturn(petReports);
        petReportService.getReportsByUserNameAndSurname("Jack", "Jones");
        verify(petReportRepository, Mockito.times(1)).findReportsByNameAndSurname("Jack", "Jones");
    }

    @Test
    void findAll(){
        when(petReportRepository.findAll()).thenReturn(petReports);
        petReportService.getAllReports();
        verify(petReportRepository, Mockito.times(1)).findAll();
    }


}
