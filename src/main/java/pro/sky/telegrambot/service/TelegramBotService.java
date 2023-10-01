package pro.sky.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


/**
 *
 * service for processing commands from user
 *
 */
@Service
public class TelegramBotService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramBotService.class);

    /**
     *
     * copy of Telegram - bot for getting user parameters
     */
    private final TelegramBot telegramBot;

    public TelegramBotService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    /**
     *
     * using for send message for user
     *
     * @param chatId
     * @param text what user send in Telegram - bot
     * @param parseMode
     */
    public void sendMessage(long chatId, String text, @Nullable ParseMode parseMode) {
        SendMessage sendMessage = new SendMessage(chatId, text);

        if (parseMode != null) {
            sendMessage.parseMode(parseMode);
        }
        SendResponse sendResponse = telegramBot.execute(sendMessage);
        if (!sendResponse.isOk()) {
            LOGGER.error("SendMessage was failed due to " + sendResponse.description());
        }
    }

    /**
     *
     * using for send message for user
     *
     * @param chatId
     * @param text what user send in Telegram - bot
     */
    public void sendMessage(long chatId, String text) {
        sendMessage(chatId, text, null);
    }

}

