package pro.sky.telegrambot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

/**
 * class for processing user's message
 */
@Component
public class Start {

    /**
     * copy of Telegram - bot for sending message
     */
    private static final Logger LOG = LoggerFactory.getLogger(Start.class);
    private final TelegramBotService telegramBotService;
    private final UserChatService userChatService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;


    public Start(TelegramBotService telegramBotService, UserChatService userChatService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.userChatService = userChatService;
        this.handlers = handlers;
    }

    /**
     * examination that user send correct message

     * @param chatId
     */
    public void acceptStartCommands(long chatId) {
            starting(chatId);
    }

    /**
     * method, if user send {@code "/start" }
     * <br>
     * use method
     *
     * @param chatId
     */
    private final void starting(long chatId) {
        userChatService.setChoseShelter(chatId);
        handlers.startCommand(chatId);
    }
}