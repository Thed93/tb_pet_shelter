package pro.sky.telegrambot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

/**
 * class for processing user's message
 */
@Component
public class Adoption {



    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * class for getting methods
     */
    private static final Logger LOG = LoggerFactory.getLogger(Adoption.class);
    private final Handlers handlers;

    private Commands commands;

    private final UserChatService userChatService;

    /**
     * class for processing user's commands
     */

    public Adoption(TelegramBotService telegramBotService, Handlers handlers, UserChatService userChatService) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
        this.userChatService = userChatService;
    }

    /**
     * redirection user depending on his message
     *
     * @param text user's message
     * @param chatId
     */
    public void adoptionMenu(String text, long chatId) throws UnsatisfiedDependencyException {

        // Пока что точно такая же история в Info, потом испраится, сейчас пока нет времени на это

        Commands currentCommand = Commands.valueOf(toConstantStyle(text.substring(1)));
        LOG.info(currentCommand.toString());

        switch (currentCommand){
            case CAT:
                welcomeRules(chatId);
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
                volunteer(chatId);
                break;
        }
    }

    private final String toConstantStyle(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) >= 65 && string.charAt(i) <= 90) {
                return (string.substring(0, i) + '_' + string.substring(i)).toUpperCase();
            }
        }
        return string.toUpperCase();
    }

    /**
     * method, if user send {@code "/welcomeRules" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#welcomeRules(Long, String)}
     *
     * @param chatId
     */
    private final void welcomeRules (Long chatId){
        handlers.welcomeRules(chatId, userChatService.getShelter(chatId));
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
     * use method
     *
     * @param chatId
     */
    private final void volunteer(Long chatId){
        handlers.volunteer(chatId);
    }


}