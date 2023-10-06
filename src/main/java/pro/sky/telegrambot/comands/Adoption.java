package pro.sky.telegrambot.comands;

import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * class for processing user's message
 */
public class Adoption {



    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    /**
     * class for processing user's commands
     */

    public Adoption(TelegramBotService telegramBotService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
    }

    /**
     * redirection user depending on his message
     *
     * @param user
     * @param text user's message
     * @param chatId
     */
    public void adoptionMenu(User user, Commands text, long chatId){
        Commands currentCommand = text;
        switch (currentCommand){
            case CAT:
                welcomeRules(chatId, user);
                break;
            case DOCS:
                docs(chatId);
                break;
            case PET_TRANSPORTATION:
                petTransportation(chatId);
                break;
            case BABY_PET_HOUSE:
                babyPetHouse(chatId);
                break;
            case PET_HOUSE:
                petHouse(chatId);
                break;
            case SPECIAL_PET_HOUSE:
                specialPetHouse(chatId);
                break;
            case ADVICE_DOG_HANDLER:
                adviceDogHandler(chatId);
                break;
            case DOG_HANDLER:
                dogHandler(chatId);
                break;
            case REFUSE_PET:
                refusePet(chatId);
                break;
            case VOLUNTEER:
                volunteer(user, chatId);
                break;
        }
    }

    /**
     * method, if user send {@code "/welcomeRules" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#welcomeRules(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void welcomeRules (Long chatId, User user){
        handlers.welcomeRules(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/docs" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#docs(Long)}
     *
     * @param chatId
     */
    private final void docs(Long chatId){
        handlers.docs(chatId);
    }

    /**
     * method, if user send {@code "/petTransportation" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#petTransportation(Long)}
     *
     * @param chatId
     */
    private final void petTransportation(Long chatId){
        handlers.petTransportation(chatId);
    }

    /**
     * method, if user send {@code "/babyPetHouse" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#babyPetHouse(Long)}
     *
     * @param chatId
     */
    private final void babyPetHouse(Long chatId){
        handlers.babyPetHouse(chatId);
    }

    /**
     * method, if user send {@code "/petHouse" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#petHouse(Long)}
     *
     * @param chatId
     */
    private final void petHouse(Long chatId) {
        handlers.petHouse(chatId);
    }
    /**
     * method, if user send {@code "/specialPetHouse" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#specialPetHouse(Long)}
     *
     * @param chatId
     */
    private final void specialPetHouse(Long chatId){
        handlers.specialPetHouse(chatId);
    }

    /**
     * method, if user send {@code "/adviceDogHandler" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#adviceDogHandler(Long)}
     *
     * @param chatId
     */
    private final void adviceDogHandler(Long chatId){
        handlers.adviceDogHandler(chatId);
    }

    /**
     * method, if user send {@code "/dogHandler" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#dogHandler(Long)}
     *
     * @param chatId
     */
    private final void dogHandler(Long chatId){
        handlers.dogHandler(chatId);
    }

    /**
     * method, if user send {@code "/refusePet" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#refusePet(Long)}
     *
     * @param chatId
     */
    private final void refusePet(Long chatId){
        handlers.refusePet(chatId);
    }

    /**
     * method, if user send {@code "/volunteer" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#volunteer(User, long)}
     *
     * @param user
     * @param chatId
     */
    private final void volunteer(User user, Long chatId){
        handlers.volunteer(user, chatId);
    }


}
