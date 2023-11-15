package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

/**
 * class for processing user's message
 */
@Component
public class ChoseShelter {


    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;
    private final UserChatService userChatService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public ChoseShelter(TelegramBotService telegramBotService, UserChatService userChatService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.userChatService = userChatService;
        this.handlers = handlers;
    }

    /**
     * examination that user send correct message
     *
     * @param text
     * @param chatId
     */
    public void acceptChoseShelterCommand(String text, long chatId){
        if(text.equals("/dog") || text.equals("/cat")){
            if (userChatService.getUserChatStatus(chatId).equals(BotState.CHOOSE_SHELTER.toString())){
                shelterType(text, chatId);
            }
        }
    }

    /**
     * redirection and send parameters for user depending on his message
     * <br>
     * use method:
     * <br>
     * {@link pro.sky.telegrambot.handle.Handlers#handleShelterConsultation(Long, String)}
     *
     * @param text
     * @param chatId
     */

    private final void shelterType(String text, long chatId) {
        if (text.equals(Commands.DOG.getCommandText())) {
            userChatService.setMenu(chatId);
            userChatService.setDog(chatId);
            handlers.handleShelterConsultation(chatId, text);
        } else if (text.equals(Commands.CAT.getCommandText())) {
            userChatService.setMenu(chatId);
            userChatService.setCat(chatId);
            handlers.handleShelterConsultation(chatId, text);
        }
    }
}