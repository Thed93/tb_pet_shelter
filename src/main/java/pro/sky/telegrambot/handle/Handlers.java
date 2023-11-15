package pro.sky.telegrambot.handle;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.AppealToVolunteer;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.service.AppealToVolunteerService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

/**
 * service of methods, which will be chosen depending on user's message
 */
@Service
@Component
public class Handlers {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * texts of methods
     */
    private final HandlerText handlerText;

    private final UserChatService userChatService;

    /**
     * service for saving appeal to volunteer
     */
    private final AppealToVolunteerService appealToVolunteerService;


    public Handlers(TelegramBotService telegramBotService, HandlerText handlerText, UserChatService userChatService, AppealToVolunteerService appealToVolunteerService) {
        this.telegramBotService = telegramBotService;
        this.handlerText = handlerText;
        this.userChatService = userChatService;
        this.appealToVolunteerService = appealToVolunteerService;
    }


    public void startCommand(Long chatId){
        telegramBotService.sendMessage(chatId, handlerText.startingText(userChatService.getName(chatId)));
    }
    public void handleShelterConsultation(Long chatId, String type) {
        String animalType = null;
        if (type.equals("/dog")) {
            animalType = "собак";
        } else {
            animalType = "кошек";
        }
        telegramBotService.sendMessage(chatId, handlerText.handleShelterConsultationText(animalType));
    }
    public void handleAdoptionConsultation(Long chatId, String currentChosenShelter) {
        telegramBotService.sendMessage(chatId, handlerText.handleAdoptionConsultationText());
    }

    public void aboutShelter(Long chatId, String shelterType) {

        telegramBotService.sendMessage(chatId, handlerText.aboutShelterText(shelterType));
    }

    public void workingHours(Long chatId, String shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.workingHoursText(shelterType));
    }

    public void securityNumber(Long chatId, String shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.securityNumberText(shelterType));
    }

    public void safetyPrecautions(Long chatId, String shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.safetyPrecautionsText(shelterType));
    }

    public void writeData(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.writeDataText());
    }

    public void howToTakePet(Long chatId, String shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.howToTakePetText(shelterType));
    }

    public void welcomeRules(Long chatId, String shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.welcomeRulesText(shelterType));
    }
    public void docs(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.docsText());
    }

    public void petTransportation(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.petTransportationText());
    }

    public void babyPetHouse(Long chatId) { telegramBotService.sendMessage(chatId, handlerText.babyPetHouseText());
    }

    public void petHouse(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.petHouseText());}

    public void specialPetHouse(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.specialPetHouseText());
    }

    public void adviceDogHandler(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.adviceDogHandlerText());

    }

    public void dogHandler(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.dogHandlerText());
    }

    public void refusePet(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.refusePetText());

    }

    public void volunteer(long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.volunteerText());
    }

    public void reportMenu(String text, long chatId){
        telegramBotService.sendMessage(chatId, handlerText.reportText());
    }
}