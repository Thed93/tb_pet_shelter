package pro.sky.telegrambot.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.repository.ProbationRepository;
import pro.sky.telegrambot.service.TelegramBotService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class Timer {

    private final ProbationRepository probationRepository;
    private final TelegramBotService telegramBotService;

    public Timer(ProbationRepository probationRepository, TelegramBotService telegramBotService) {
        this.probationRepository = probationRepository;
        this.telegramBotService = telegramBotService;
    }


    @Scheduled(cron = "00 00 20 * * *")
    public void checkLastPetReport() {
        List<Probation> probation = probationRepository.findAll();

        probation.forEach(e -> {
            LocalDateTime currentDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime lastReportDay = e.getLastReportDate().truncatedTo(ChronoUnit.DAYS);

            if (lastReportDay.equals(currentDay)) {
                telegramBotService.sendMessage(e.getUserChat().getUserId(), "Напоминаю про отчет");
            } else if (lastReportDay.plusDays(1).equals(currentDay)) {
                telegramBotService.sendMessage(e.getUserChat().getUserId(), "Второй день от вас нет отчета");
            } else if (lastReportDay.plusDays(2).equals(currentDay)) {
                telegramBotService.sendMessage(e.getUserChat().getUserId(), "Жалуюсь волонтеру");
            }
        });
    }

    @Scheduled(cron = "00 00 12 * * *")
    public void checkEndProbation() {
        List<Probation> probation = probationRepository.findAll();

        probation.forEach(e -> {
            LocalDateTime currentDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            LocalDateTime endProbationDate = e.getProbationEndDate().truncatedTo(ChronoUnit.DAYS);

            if (endProbationDate.equals(currentDay)) {
                //TODO: Добавить функцию для волонтера по решению оставить животное у юзера или нет
            }
        });
    }
}
