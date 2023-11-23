package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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
    private final PetReportRepository petReportRepository;
    private final UserChatService userChatService;
    private final TelegramBot telegramBot;

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;
    private final String photoDir;


    public PetReportService(PetReportRepository petReportRepository,
                            UserChatService userChatService,
                            TelegramBot telegramBot, TelegramBotService telegramBotService,
                            @Value("${path.to.report.photos.folder}") String photoDir) {
        this.petReportRepository = petReportRepository;
        this.userChatService = userChatService;
        this.telegramBot = telegramBot;
        this.telegramBotService = telegramBotService;
        this.photoDir = photoDir;
    }

    public void newReport(Long chatId) {
        PetReport petReport = new PetReport();
        UserChat user = userChatService.findById(chatId);
        petReport.setUser(user);
        petReport.setDateTime(LocalDateTime.now());
        petReport.setStatus("IN_PROGRESS");
        petReportRepository.save(petReport);
        userChatService.setUserChatStatus(chatId, BotState.REPORT_PHOTO);
        telegramBotService.sendMessage(chatId, "Отправте фото");
    }

    public void reportText(String text, Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        PetReport petReport = petReportRepository.findPetReportByUserAndStatus(userChat, "IN_PROGRESS");

        if (petReport != null) {
            petReport.setText(text);
            petReport.setStatus("FULL_INFO");
            petReportRepository.save(petReport);
            userChatService.setUserChatStatus(chatId, BotState.MENU);
            telegramBotService.sendMessage(chatId, "Отчет сформирован");
        }
    }

    public void reportPhoto(PhotoSize[] photoSizes, Long chatId) {
        if (photoSizes != null) {
            PhotoSize photoSize = photoSizes[photoSizes.length - 1];
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
            try {
                String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());
                byte[] data = telegramBot.getFileContent(getFileResponse.file());
                savePhoto(chatId, data, extension);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            telegramBotService.sendMessage(chatId, "Пришлите фото");
        }
    }

    private void savePhoto(Long chatId, byte[] data, String extension) throws IOException {
        UserChat userChat = userChatService.findById(chatId);
        PetReport petReport = petReportRepository.findPetReportByUserAndStatus(userChat, "IN_PROGRESS");
        if (petReport != null) {
            Path filePath = Path.of(photoDir, petReport.hashCode() + "." + extension);
            Files.createDirectories(filePath.getParent());
            Files.deleteIfExists(filePath);
            try (
                    InputStream is = new ByteArrayInputStream(data);
                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                    BufferedInputStream bis = new BufferedInputStream(is, 1024);
                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
            ) {
                bis.transferTo(bos);
                petReport.setPhotoPath(filePath.toString());
                petReportRepository.save(petReport);
                userChatService.setUserChatStatus(chatId, BotState.REPORT_TEXT);
                telegramBotService.sendMessage(chatId, "Введите текст");
            }
        } else {
            //TODO: Доделать здесь
        }
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
/*    @Transactional
    public void savePetReport(PetReport petReport) {
        List<PetReport> ownerReports = petReportRepository.findReportsByUserNameAndUserSurname(petReport.getUser().getName(), petReport.getUser().getSurname());
        PetReport lastReport = ownerReports.get(0);
        for (int i = 0; i < ownerReports.size(); i++) {
            if (lastReport.getReportNumber() < ownerReports.get(i).getReportNumber()) {
                lastReport = ownerReports.get(i);
            }
        }*/
        /*if (!lastReport.getDateTime().equals(petReport.getDateTime())) {
            if (petReport.getPhoto().equals(Optional.empty())) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Добавьте фото к вашему отчету и отправьте их вместе!");
            } else if (petReport.getText().isEmpty()) {
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Добавьте текст к вашему отчету и отправьте их вместе!");
            } else {
                petReportRepository.save(petReport);
                telegramBotService.sendMessage(petReport.getUser().getUserId(), "Спасибо за ваш отчет!");
            }
        }*/
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


//    }

    /**
     * get all saving reports from all users
     * <br>
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all saving reports
     */
    public Collection<PetReport> getAllReports() {
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