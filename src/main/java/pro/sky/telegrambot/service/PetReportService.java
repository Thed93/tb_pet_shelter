package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.model.PhotoSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.repository.PetReportRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PetReportService {

    private static final Logger logger = LoggerFactory.getLogger(PetReportService.class);

    private final PetReportRepository petReportRepository;

    @Autowired
    private TelegramBotService telegramBotService;

    public PetReportService(PetReportRepository petReportRepository) {
        this.petReportRepository = petReportRepository;
    }

    @Transactional
    public void savePetReport(String owner, LocalDateTime dateTime, PhotoSize[] photo, String text, long chatId) {
        PetReport newReport = new PetReport(owner, dateTime.truncatedTo(ChronoUnit.DAYS), photo, text);
        List<PetReport> ownerReports = petReportRepository.findReportsByOwner(owner);
        PetReport lastReport = null;
        for (int i = 0; i < ownerReports.size(); i++) {
            if (lastReport.getReportNumber() < ownerReports.get(i).getReportNumber()) {
                lastReport = ownerReports.get(i);
            }
        }
        if (!lastReport.getDateTime().equals(newReport.getDateTime())) {
            if (newReport.getPhoto().equals(Optional.empty())) {
                telegramBotService.sendMessage(chatId, "Добавьте фото к вашему отчету и отправьте их вместе!");
            } else if (newReport.getText().isEmpty()) {
                telegramBotService.sendMessage(chatId, "Добавьте текст к вашему отчету и отправьте их вместе!");
            } else {
                petReportRepository.save(newReport);
                telegramBotService.sendMessage(chatId, "Спасибо за ваш отчет!");
            }
        }
//        if (lastReport.getDateTime().equals(newReport.getDateTime()) && !lastReport.getText().isEmpty() && !lastReport.getPhoto().isEmpty()) {
//            telegramBotService.sendMessage(chatId, "Вы уже отправили отчет за сегодня, спасибо!");
//        }
//        if (lastReport.getDateTime().equals(newReport.getDateTime()) && lastReport.getText().isEmpty() && !newReport.getText().isEmpty()) {
//            lastReport.setText(newReport.getText());
//            telegramBotService.sendMessage(chatId, "Текст добавлен в ваш сегодняшний отчет!");
//            if (lastReport.getPhoto().isEmpty()) {
//                telegramBotService.sendMessage(chatId, "Пожалуйста, приложите фото к вашему отчету.");
//            }
//        }
//        if (lastReport.getDateTime().equals(newReport.getDateTime()) && lastReport.getPhoto().isEmpty() && !newReport.getPhoto().isEmpty()) {
//            lastReport.setPhoto(newReport.getPhoto());
//            telegramBotService.sendMessage(chatId, "Фото добавлено в ваш сегодняшний отчет!");
//            if (lastReport.getText().isEmpty()) {
//                telegramBotService.sendMessage(chatId, "Пожалуйста, добавьте текст к вашему отчету.");
//            }
//        }
//        if (lastReport.getDateTime().equals(newReport.getDateTime()) && !lastReport.getText().isEmpty() && !newReport.getText().isEmpty() && !newReport.getPhoto().isEmpty()) {
//            telegramBotService.sendMessage(chatId, "Вы уже добавили текст к вашему сегодняшнему отчету.");
//            if (lastReport.getPhoto().isEmpty()) {
//                telegramBotService.sendMessage(chatId, "Пожалуйста, приложите фото к вашему отчету.");
//            }
//        }
//        if (lastReport.getDateTime().equals(newReport.getDateTime()) && lastReport.getPhoto().isEmpty() && newReport.getPhoto().isEmpty()) {
//            telegramBotService.sendMessage(chatId, "Вы уже добавили фото к вашему сегодняшнему отчету.");
//            if (lastReport.getText().isEmpty()) {
//                telegramBotService.sendMessage(chatId, "Пожалуйста, добавьте текст к вашему отчету.");
//            }


        }


    }

