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
import pro.sky.telegrambot.commands.ChoseShelter;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
    private final Handlers handlers;

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;
    private final ProbationService probationService;
    private final ChoseShelter choseShelter;
    private final String photoDir;


    public PetReportService(PetReportRepository petReportRepository,
                            UserChatService userChatService,
                            TelegramBot telegramBot, Handlers handlers,
                            TelegramBotService telegramBotService,
                            ProbationService probationService,
                            ChoseShelter choseShelter,
                            @Value("${path.to.report.photos.folder}") String photoDir) {
        this.petReportRepository = petReportRepository;
        this.userChatService = userChatService;
        this.telegramBot = telegramBot;
        this.handlers = handlers;
        this.telegramBotService = telegramBotService;
        this.probationService = probationService;
        this.choseShelter = choseShelter;
        this.photoDir = photoDir;
    }

    public void choicePet(Long chatId) {
        List<Pet> pets = probationService.getProbationByUserId(chatId).stream()
                .map(Probation::getPet)
                .sorted()
                .collect(Collectors.toList());

        if (pets.size() > 0) {
            handlers.choicePet(chatId, pets);
            userChatService.setChoosePet(chatId);
        } else {
            telegramBotService.sendMessage(chatId, "У вас нет животных, по которым нужен отчет");
            userChatService.setChoseShelter(chatId);
            choseShelter.acceptChoseShelterCommand(ShelterType.valueOf(userChatService.getShelter(chatId)).toString(), chatId);
        }
    }

    public void createReport(String text, Long chatId) {
        List<Pet> pets = probationService.getProbationByUserId(chatId).stream()
                .map(Probation::getPet)
                .sorted()
                .collect(Collectors.toList());
        Pet pet = pets.get(Integer.parseInt(text) - 1);
        PetReport petReport = petReportRepository.findPetReportByPetAndStatus(pet, PetReportState.IN_PROGRESS.name());

        if (petReport == null) {
            petReport = new PetReport();
            newReport(petReport, pet, chatId);
        } else {
            petReportRepository.delete(petReport);
        }
    }

    public void complementReport(String text, PhotoSize[] photoSizes, Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        PetReport petReport = petReportRepository.findPetReportByUserChatAndStatus(userChat, PetReportState.IN_PROGRESS.name());

        if (petReport.getPhotoPath() == null) {
            reportPhoto(userChat, petReport, photoSizes);
        } else if (petReport.getDiet() == null) {
            reportDiet(userChat, petReport, text);
        } else if (petReport.getWellBeing() == null) {
            reportWellBeing(userChat, petReport, text);
        } else if (petReport.getChangeInBehavior() == null) {
            reportChangeInBehavior(userChat, petReport, text);
        }
    }

    private void newReport(PetReport petReport, Pet pet, Long chatId) {
        UserChat user = userChatService.findById(chatId);
        petReport.setPet(pet);
        petReport.setUserChat(user);
        petReport.setDateTime(LocalDateTime.now());
        petReport.setStatus(PetReportState.IN_PROGRESS.name());
        petReportRepository.save(petReport);
        userChatService.setReport(chatId);
        handlers.reportMenu(chatId);
    }

    public void reportPhoto(UserChat userChat, PetReport petReport, PhotoSize[] photoSizes) {
        if (photoSizes != null) {
            PhotoSize photoSize = photoSizes[photoSizes.length - 1];
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
            try {
                String extension = StringUtils.getFilenameExtension(getFileResponse.file().filePath());
                byte[] data = telegramBot.getFileContent(getFileResponse.file());
                savePhoto(userChat.getUserId(), petReport, data, extension);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            telegramBotService.sendMessage(userChat.getUserId(), "Пришлите фото");
        }
    }

    private void savePhoto(Long chatId, PetReport petReport, byte[] data, String extension) throws IOException {
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
            handlers.waitingForDiet(chatId);
        }
    }

    public void reportDiet(UserChat userChat, PetReport petReport, String text) {
        petReport.setDiet(text);
        petReportRepository.save(petReport);
        handlers.waitingForWellBeing(userChat.getUserId());
    }

    private void reportWellBeing(UserChat userChat, PetReport petReport, String text) {
        petReport.setWellBeing(text);
        petReportRepository.save(petReport);
        handlers.waitingForChangeInBehavior(userChat.getUserId());
    }

    private void reportChangeInBehavior(UserChat userChat, PetReport petReport, String text) {
        Long chatId = userChat.getUserId();
        petReport.setChangeInBehavior(text);
        petReport.setStatus(PetReportState.Full_INFO.name());
        petReportRepository.save(petReport);
        probationService.setLastReportDate(petReport.getPet(), LocalDateTime.now());
        userChatService.setChoseShelter(chatId);
        handlers.reportAccepted(userChat.getUserId());
        choseShelter.acceptChoseShelterCommand(ShelterType.valueOf(userChatService.getShelter(chatId)).toString(), chatId);
    }

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


    /**
     *
     * get all saving reports from person, which we found by name and surname
     * <br>
     * use repository method {@link pro.sky.telegrambot.repository.PetReportRepository#findReportsByUserNameAndUserSurname(String, String)}
     *
     * @return all saving reports from chosen person
     */


}