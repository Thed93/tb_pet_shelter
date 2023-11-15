package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.repository.PetReportRepository;
import pro.sky.telegrambot.repository.UserChatRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * service for processing commands
 */
@Service
public class PetReportService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetReportService.class);

    /**
     * repository for data access
     */
    private final UserChatRepository userChatRepository;
    private final PetReportRepository petReportRepository;

    /**
     *
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;


    public PetReportService(UserChatRepository userChatRepository, PetReportRepository petReportRepository, TelegramBotService telegramBotService) {
        this.userChatRepository = userChatRepository;
        this.petReportRepository = petReportRepository;
        this.telegramBotService = telegramBotService;
    }

    public void createPetReport(Long chatId) {
        LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        UserChat user = userChatRepository.findUserChatByUserId(chatId).get();
        PetReport petReport = new PetReport(user, dateTime, null, null, null, null);
        petReportRepository.save(petReport);
    }

    /**
     *
     * save the report of the person who adopted the pet from the shelter
     * <br>
     * use repository methods:
     * <br>
     * {@link pro.sky.telegrambot.repository.PetReportRepository#findReportsByUserNameAndUserSurname(String, String)}
     * <br>
     * {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param petReport building in {@link pro.sky.telegrambot.listener.TelegramBotUpdatesListener}
     */

    public void savePhoto() {

    }

    @Transactional
    public void savePetReport(PetReport petReport) {
        List<PetReport> ownerReports = petReportRepository.findReportsByUserNameAndUserSurname(petReport.getUser().getName(), petReport.getUser().getSurname());
        PetReport lastReport = ownerReports.get(0);
        for (int i = 0; i < ownerReports.size(); i++) {
            if (lastReport.getReportNumber() < ownerReports.get(i).getReportNumber()) {
                lastReport = ownerReports.get(i);
            }
        }
        if (!lastReport.getDateTime().equals(petReport.getDateTime())) {
            if (petReport.getPhoto().equals(Optional.empty())) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Отправьте, пожалуйста, фото питомца");
            } else if (petReport.getDiet().isEmpty()) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Опишите рацион животного");
            } else if (petReport.getWellBeing().isEmpty()) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Опишите общее самочувствие и привыкание к новому месту");
            } else if (petReport.getChangeInBehavior().isEmpty()) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Опишите изменение в поведении: отказ от старых привычек, приобретение новых");
            } else {
                petReportRepository.save(petReport);
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Спасибо за ваш отчет!");
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

    /**
     *
     * get all saving reports from all users
     * <br>
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all saving reports
     */
    public Collection<PetReport> getAllReports(){
        return petReportRepository.findAll();
    }

    /**
     *
     * get all saving reports from person, which we found by name and surname
     * <br>
     * use repository method {@link pro.sky.telegrambot.repository.PetReportRepository#findReportsByUserNameAndUserSurname(String, String)}
     *
     * @return all saving reports from chosen person
     */



}