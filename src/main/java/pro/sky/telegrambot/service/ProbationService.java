package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.repository.ProbationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProbationService {

    private final ProbationRepository probationRepository;
    private final UserChatService userChatService;
    private final AdoptedService adoptedService;
    private final TelegramBotService telegramBotService;

    public ProbationService(ProbationRepository probationRepository,
                            UserChatService userChatService,
                            AdoptedService adoptedService,
                            TelegramBotService telegramBotService) {
        this.probationRepository = probationRepository;
        this.userChatService = userChatService;
        this.adoptedService = adoptedService;
        this.telegramBotService = telegramBotService;
    }

    public Probation createProbation(UserChat userChat, Pet pet) {
        Probation probation = new Probation();
        probation.setUserChat(userChat);
        probation.setPet(pet);
        probation.setProbationEndDate(LocalDateTime.now().plusDays(30));

        return probationRepository.save(probation);
    }

    public boolean increaseProbation(Probation probation, int days) {
        if (days <= 30 && days > 0) {
            probation.setProbationEndDate(probation.getProbationEndDate().plusDays(days));
            probationRepository.save(probation);
            return true;
        }
        return false;
    }

    public void adoptPet(Pet pet) {
        UserChat userChat = probationRepository.findByPet(pet).getUserChat();
        Probation probation = probationRepository.findByPet(pet);
        adoptedService.createAdopted(userChat, pet);
        probationRepository.delete(probation);
    }

    public List<Probation> getProbationByUserId(Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        return probationRepository.findAllByUserChat(userChat);
    }

    public void setLastReportDate(Pet pet, LocalDateTime localDateTime) {
        Probation probation = probationRepository.findByPet(pet);
        probation.setLastReportDate(localDateTime);
        probationRepository.save(probation);
    }

    public List<Probation> getProbationByVolunteer(Volunteer volunteer) {
        return probationRepository.findAllByVolunteer(volunteer);
    }

    public Probation getProbationByPet(Pet pet) {
        return probationRepository.findByPet(pet);
    }

    public Probation getProbationByVolunteerAndStatus(Volunteer volunteer, String status) {
        return probationRepository.findFirstByVolunteerAndStatus(volunteer, status);
    }

    public void setStatus(Probation probation, String status) {
        probation.setStatus(status);
        probationRepository.save(probation);
    }

    public void deleteProbation(Probation probation) {
        probationRepository.delete(probation);
    }

    public void denyAdopt(Probation probation) {
        Long chatId = probation.getUserChat().getUserId();
        telegramBotService.sendMessage(chatId, "Вам отказано в усыновлении");
    }
}
