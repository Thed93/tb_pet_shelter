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
     * @param user
     * @param text
     * @param chatId
     */
    public void acceptChoseShelterCommand(UserChat user, String text, long chatId){
        if(text.equals("/dog") || text.equals("/cat") && user.getBotState().equals(BotState.CHOOSE_SHELTER.toString())){
            shelterType(user, text, chatId);
        }
    }

    /**
     * redirection and send parameters for user depending on his message
     * <br>
     * use method:
     * <br>
     * {@link pro.sky.telegrambot.handle.Handlers#handleShelterConsultation(Long, String)}
     *
     * @param user
     * @param text
     * @param chatId
     */

    private final void shelterType(UserChat user, String text, long chatId) {
        if (text.equals(Commands.DOG.getCommandText())) {
            user.setBotState(BotState.MENU.toString());
            user.setCurrentChosenShelter(ShelterType.DOG_SHELTER.toString());
            user.setHasChosenShelter(true);
            userChatService.saveUser(user);
            handlers.handleShelterConsultation(chatId, text);
        } else if (text.equals(Commands.CAT.getCommandText())) {
            user.setBotState(BotState.MENU.toString());
            user.setCurrentChosenShelter(ShelterType.CAT_SHELTER.toString());
            user.setHasChosenShelter(true);
            userChatService.saveUser(user);
            handlers.handleShelterConsultation(chatId, text);
        }
    }
}