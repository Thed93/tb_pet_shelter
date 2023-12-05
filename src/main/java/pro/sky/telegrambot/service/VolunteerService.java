package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.*;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;
    private final UserChatService userChatService;
    private final ProbationService probationService;
    private final PetReportService petReportService;
    private final TelegramBotService telegramBotService;

    public VolunteerService(VolunteerRepository volunteerRepository, UserChatService userChatService, ProbationService probationService, PetReportService petReportService, TelegramBotService telegramBotService) {
        this.volunteerRepository = volunteerRepository;
        this.userChatService = userChatService;
        this.probationService = probationService;
        this.petReportService = petReportService;
        this.telegramBotService = telegramBotService;
    }

    public Volunteer getVolunteer(Long volunteerId) {
        return volunteerRepository.findById(volunteerId).orElse(null);
    }

    public void callVolunteer(Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        Volunteer volunteer = volunteerRepository.findByUser(null);
        volunteer.setUser(userChat);
        volunteer.setState("CONVERSATION_WITH_USER");
        volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteerByUser(UserChat userChat) {
        return volunteerRepository.findByUser(userChat);
    }

    public void stopConversation(Long volunteerId) {
        Volunteer volunteer = getVolunteer(volunteerId);
        volunteer.setUser(null);
        volunteer.setState("MENU");
        volunteerRepository.save(volunteer);
    }

    public void setState(Volunteer volunteer, String state) {
        volunteer.setState(state);
        volunteerRepository.save(volunteer);
    }

    public void setState(Long volunteerId, String state) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        volunteer.setState(state);
        volunteerRepository.save(volunteer);
    }

    public void sendUnverifiedReport(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.getById(volunteerId);
        PetReport report = petReportService.getReportByVolunteerAndStatus(volunteer, "FULL_INFO");
        if (report != null) {
            petReportService.setStatus(report, "IN_INSPECTION");
            petReportService.sendReport(volunteerId, report);
        } else {
            telegramBotService.sendMessage(volunteerId, "Непроверенных отчетов нет");
            setState(volunteerId, "MENU");
        }
    }

    public void approveReport(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.getById(volunteerId);
        PetReport report = petReportService.getReportByVolunteerAndStatus(volunteer, "IN_INSPECTION");
        if (report != null) {
            petReportService.setStatus(report, "APPROVE");
            sendUnverifiedReport(volunteerId);
        }
    }

    public void denyReport(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.getById(volunteerId);
        PetReport report = petReportService.getReportByVolunteerAndStatus(volunteer, "IN_INSPECTION");
        if (report != null) {
            petReportService.setStatus(report, "DENY");
            petReportService.denyReport(report);
            sendUnverifiedReport(volunteerId);
        }
    }

    public void decideFate(Probation probation) {

    }

    public void contactWithUser(Probation probation) {
        //Long volunteerId = probation.getVolunteer().getId();
        //Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        //Long chatId = probation.getId();
        //UserChat userChat = userChatService.findById(chatId);
        //volunteer.setUser(userChat);
        //telegramBotService.sendMessage(volunteerId, "Необходимо связаться с усыновителем по поводу отссутствия отчетов");

    }

    public void sendUserContactsWithoutReports(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        Probation probation = probationService.getProbationByVolunteerAndStatus(volunteer, "NEED_REPORT");
        if (probation != null) {
            UserChat userChat = probation.getUserChat();
//        telegramBotService.sendMessage(volunteerId, userChat.getContact());TODO: Добавить юзеру контактные данные
            telegramBotService.sendMessage(volunteerId, userChat.getName() + "\n/start"); // Удалить после добавления юзеру контактных данных
            probationService.setStatus(probation, "UNDER_CONTROL");
        } else {
            telegramBotService.sendMessage(volunteerId, "Все справляются с отчетами");
        }
    }

    public void endOfProbation(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        Probation probation = probationService.getProbationByVolunteerAndStatus(volunteer, "END_OF_PROBATION");
        if (probation != null) {
            probationService.setStatus(probation, "ON_THE_DECISION");
            telegramBotService.sendMessage(volunteerId, String.format("Усыновитель: %s\nДомашнее животное: %s\n/approve\t/deny\t/extend", probation.getUserChat().getName(), probation.getPet().getName()));
        } else {
            telegramBotService.sendMessage(volunteerId, "Пока что нет тех у кого закончился испытательный срок");
            setState(volunteerId, "MENU");
        }
    }

    public void approveAdopt(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        Probation probation = probationService.getProbationByVolunteerAndStatus(volunteer, "ON_THE_DECISION");
        if (probation != null) {
            Pet pet = probation.getPet();
            probationService.adoptPet(pet);
            endOfProbation(volunteerId);
        }
    }

    public void denyAdopt(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        Probation probation = probationService.getProbationByVolunteerAndStatus(volunteer, "ON_THE_DECISION");
        if (probation != null) {
            probationService.denyAdopt(probation);
            probationService.deleteProbation(probation);
            endOfProbation(volunteerId);
        }
    }

    public void extendProbation(Long volunteerId, String text) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId).orElseThrow();
        Probation probation = probationService.getProbationByVolunteerAndStatus(volunteer, "ON_THE_DECISION");
        int days = 0;
        try {
            days = Integer.parseInt(text);
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        if (probationService.increaseProbation(probation, days)) {
            setState(volunteerId, "END_OF_PROBATION");
            probationService.setStatus(probation, null);
            endOfProbation(volunteerId);
        } else {
            telegramBotService.sendMessage(volunteerId, "Введите число в диапазоне от 1 до 30");
        }
    }
}
