package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * class for processing user's message
 */
@Component
public class Start {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;


    public Start(TelegramBotService telegramBotService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
    }

    /**
     * examination that user send correct message
     *
     * @param user
     * @param chatId
     */
    public void acceptStartCommands(UserChat user, long chatId) {
            starting(user, chatId);
    }

    /**
     * method, if user send {@code "/start" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#startCommand(Long, UserChat)}
     *
     * @param user
     * @param chatId
     */
    private final void starting(UserChat user, long chatId) {
        user.setBotState(BotState.CHOOSE_SHELTER.toString());
        user.setHasChosenShelter(false);
        user.setCurrentChosenShelter(null);
        handlers.startCommand(chatId, user);
    }
}