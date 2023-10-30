package pro.sky.telegrambot.comands;

import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * class for processing user's message
 */
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
     * @param text
     * @param chatId
     */
    public void acceptStartCommands(User user, String text, long chatId) {
        if (text.equals("/start")) {
            starting(user, text, chatId);
        }
    }

    /**
     * method, if user send {@code "/start" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#startCommand(Long, String)}
     *
     * @param user
     * @param text
     * @param chatId
     */
    private final void starting(User user, String text, long chatId) {
        user.setBotState(BotState.CHOOSE_SHELTER);
        user.setHasChosenShelter(false);
        user.setCurrentChosenShelter(null);
        handlers.startCommand(chatId, user.getName());
    }
}




