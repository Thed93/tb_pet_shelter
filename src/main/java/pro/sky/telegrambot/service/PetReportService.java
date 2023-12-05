package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pro.sky.telegrambot.commands.ChoseShelter;
import pro.sky.telegrambot.entity.*;
import pro.sky.telegrambot.enums.PetReportState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.repository.PetReportRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * service for working with reports on pets
 */
@Service
public class PetReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetReportService.class);
    private final PetReportRepository petReportRepository;
    private final UserChatService userChatService;
    private final TelegramBot telegramBot;
    private final Handlers handlers;
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

    /**
     * method for asking the user for which pet he wants to send a report to
     *
     * @param chatId
     */
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

    /**
     * method which creates empty report for a pet that the user has selected
     *
     * @param text
     * @param chatId
     */
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

    /**
     * method which accepts an empty report and fills it with initial data
     *
     * @param petReport
     * @param pet
     * @param chatId
     */
    private void newReport(PetReport petReport, Pet pet, Long chatId) {
        UserChat user = userChatService.findById(chatId);
        Volunteer volunteer = probationService.getProbationByPet(pet).getVolunteer();
        petReport.setPet(pet);
        petReport.setUserChat(user);
        petReport.setDateTime(LocalDateTime.now());
        petReport.setStatus(PetReportState.IN_PROGRESS.name());
        petReport.setVolunteer(volunteer);
        petReportRepository.save(petReport);
        userChatService.setReport(chatId);
        handlers.reportMenu(chatId);
    }

    /**
     * method which calls other methods to fill out the report
     *
     * @param text
     * @param photoSizes
     * @param chatId
     */

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

    /**
     * method which takes an array of photos and calls a method to save one photo
     *
     * @param userChat
     * @param petReport
     * @param photoSizes
     */
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

    /**
     * method which saves photo in the file system
     *
     * @param chatId
     * @param petReport
     * @param data
     * @param extension
     * @throws IOException
     */
    private void savePhoto(Long chatId, PetReport petReport, byte[] data, String extension) throws IOException {
        Path filePath = Path.of(photoDir, petReport.hashCode() + "." + extension);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = new ByteArrayInputStream(data);
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
            petReport.setPhotoPath(filePath.toString());
            petReportRepository.save(petReport);
            handlers.waitingForDiet(chatId);
        }
    }

    /**
     * method which saves the pet's diet in the report
     *
     * @param userChat
     * @param petReport
     * @param text
     */
    public void reportDiet(UserChat userChat, PetReport petReport, String text) {
        petReport.setDiet(text);
        petReportRepository.save(petReport);
        handlers.waitingForWellBeing(userChat.getUserId());
    }

    /**
     * method which saves the pet's well-being in the report
     *
     * @param userChat
     * @param petReport
     * @param text
     */
    private void reportWellBeing(UserChat userChat, PetReport petReport, String text) {
        petReport.setWellBeing(text);
        petReportRepository.save(petReport);
        handlers.waitingForChangeInBehavior(userChat.getUserId());
    }

    /**
     * method which saves the pet's change in behavior in the report
     *
     * @param userChat
     * @param petReport
     * @param text
     */
    private void reportChangeInBehavior(UserChat userChat, PetReport petReport, String text) {
        Long chatId = userChat.getUserId();
        petReport.setChangeInBehavior(text);
        petReport.setStatus(PetReportState.FULL_INFO.name());
        petReportRepository.save(petReport);
        probationService.setLastReportDate(petReport.getPet(), LocalDateTime.now());
        userChatService.setChoseShelter(chatId);
        handlers.reportAccepted(userChat.getUserId());
        choseShelter.acceptChoseShelterCommand(ShelterType.valueOf(userChatService.getShelter(chatId)).toString(), chatId);
    }

    public PetReport getReportByVolunteerAndStatus(Volunteer volunteer, String state) {
        return petReportRepository.findFirstPetReportByVolunteerAndStatus(volunteer, state);
    }

    public File getPhoto(PetReport report) {
        Path path = Path.of(report.getPhotoPath());
        return path.toFile();
    }

    public void sendReport(Long volunteerId, PetReport report) {
        File photo = getPhoto(report);
        String text = String.format("Рацион животного:\n%s\n\n" +
                "Общее самочувствие и привыкание к новому месту:\n%s\n\n" +
                "Изменения в поведении:\n%s\n\n", report.getDiet(), report.getWellBeing(), report.getChangeInBehavior());
        telegramBotService.sendPhoto(volunteerId, photo);
        telegramBotService.sendMessage(volunteerId,  text + "/approve\t/deny");
    }

    public void setStatus(PetReport report, String inInspection) {
        report.setStatus(inInspection);
        petReportRepository.save(report);
    }

    public void denyReport(PetReport report) {
        Long chatId = report.getUserChat().getUserId();
        String petName = report.getPet().getName();
        telegramBotService.sendMessage(chatId, "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                "Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного");
    }
}