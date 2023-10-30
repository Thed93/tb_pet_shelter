package pro.sky.telegrambot.handle;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.AppealToVolunteer;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.AppealToVolunteerService;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * service of methods, which will be chosen depending on user's message
 */
@Service
public class Handlers {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * texts of methods
     */
    private final HandlerText handlerText;

    /**
     * service for saving appeal to volunteer
     */
    private final AppealToVolunteerService appealToVolunteerService;


    public Handlers(TelegramBotService telegramBotService, HandlerText handlerText, AppealToVolunteerService appealToVolunteerService) {
        this.telegramBotService = telegramBotService;
        this.handlerText = handlerText;
        this.appealToVolunteerService = appealToVolunteerService;
    }


    public void startCommand(Long chatId, String name){
        telegramBotService.sendMessage(chatId, handlerText.startingText(name));
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
    public void handleAdoptionConsultation(Long chatId, ShelterType currentChosenShelter) {
        telegramBotService.sendMessage(chatId, handlerText.handleAdoptionConsultationText());
    }

    public void aboutShelter(Long chatId, ShelterType shelterType) {

        telegramBotService.sendMessage(chatId, handlerText.aboutShelterText(shelterType));
    }

    public void workingHours(Long chatId, ShelterType shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.workingHoursText(shelterType));
    }

    public void securityNumber(Long chatId, ShelterType shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.securityNumberText(shelterType));
    }

    public void safetyPrecautions(Long chatId, ShelterType shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.safetyPrecautionsText(shelterType));
    }

    public void writeData(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.writeDataText());
    }

    public void howToTakePet(Long chatId, ShelterType shelterType) {
        telegramBotService.sendMessage(chatId, handlerText.howToTakePetText(shelterType));
    }

    public void welcomeRules(Long chatId, ShelterType shelterType) {
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

    public void volunteer(UserChat user, long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.volunteerText());
        AppealToVolunteer appealToVolunteer = new AppealToVolunteer(user);
        appealToVolunteerService.saveAppeal(appealToVolunteer);
    }

    public void reportMenu(UserChat user, String text, long chatId){
        telegramBotService.sendMessage(chatId, handlerText.reportText());
    }
}

