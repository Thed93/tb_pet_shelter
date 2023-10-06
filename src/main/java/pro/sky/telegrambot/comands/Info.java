package pro.sky.telegrambot.comands;

import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 *
 * class for processing user's message
 *
 */
public class Info {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    private final Commands commands;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public Info(TelegramBotService telegramBotService, Commands commands, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.commands = commands;
        this.handlers = handlers;
    }

    /**
     * redirection user depending on his message
     * 
     * @param user
     * @param text user's message
     * @param chatId
     */
    public void acceptInfoCommands(User user, Commands text, Long chatId){
        switch (text){
            case ABOUT:
                getShelterInfo(chatId, user);
                break;
            case WORKING_HOURS:
                workingHours(chatId, user);
                break;
            case SECURITY_NUMBER:
                securityNumber(chatId, user);
                break;
            case SAFETY_PRECAOTIONS:
                safetyPrecautions(chatId, user);
                break;
            case HELP:
                help(chatId);
                break;
        }
    }


    /**
     * method, if user send {@code "/about" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#aboutShelter(Long, ShelterType)} 
     * 
     * @param chatId
     * @param user
     */
    private final void getShelterInfo (Long chatId, User user){
        handlers.aboutShelter(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/workingHours" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#workingHours(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void workingHours(Long chatId, User user){
        handlers.workingHours(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/securityNumber" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#securityNumber(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void securityNumber (Long chatId, User user){
        handlers.securityNumber(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/safetyPrecautions" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#safetyPrecautions(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void safetyPrecautions (Long chatId, User user){
        handlers.safetyPrecautions(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/help" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#writeData(Long)}
     *
     * @param chatId
     */
    private final void help(Long chatId){
        handlers.writeData(chatId);
    }
}
