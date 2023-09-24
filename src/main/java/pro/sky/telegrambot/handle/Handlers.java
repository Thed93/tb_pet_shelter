package pro.sky.telegrambot.handle;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.listener.ShelterType;
import pro.sky.telegrambot.service.TelegramBotService;

@Service
public class Handlers {

    private final TelegramBotService telegramBotService;

    private final HandlerText handlerText;


    public Handlers(TelegramBotService telegramBotService, HandlerText handlerText) {
        this.telegramBotService = telegramBotService;
        this.handlerText = handlerText;
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
    public void handleAdoptionConsultation(Long chatId, String type) {
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

    public void volunteer(Long chatId) {
        telegramBotService.sendMessage(chatId, handlerText.volunteerText());
    }
}
