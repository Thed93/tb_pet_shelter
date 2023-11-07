package pro.sky.telegrambot.commands;;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 *
 * class for processing user's message
 *
 */
@Component
public class Info {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;


    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public Info(TelegramBotService telegramBotService,Handlers handlers) {
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
    public void acceptInfoCommands(UserChat user, String text, Long chatId){
        Commands currentCommand = Commands.valueOf(text);
        switch (currentCommand){
            case ABOUT:
                getShelterInfo(chatId, user);
                break;
            case WORKING_HOURS:
                workingHours(chatId, user);
                break;
            case SECURITY_NUMBER:
                securityNumber(chatId, user);
                break;
            case SAFETY_PRECAUTIONS:
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
     * use method {@link pro.sky.telegrambot.handle.Handlers#aboutShelter(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void getShelterInfo (Long chatId, UserChat user){
        handlers.aboutShelter(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/workingHours" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#workingHours(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void workingHours(Long chatId, UserChat user){
        handlers.workingHours(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/securityNumber" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#securityNumber(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void securityNumber (Long chatId, UserChat user){
        handlers.securityNumber(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/safetyPrecautions" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#safetyPrecautions(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void safetyPrecautions (Long chatId, UserChat user){
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