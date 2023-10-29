package pro.sky.telegrambot.comands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * class for processing user's message
 */
@Component
public class ChoseShelter {


    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public ChoseShelter(TelegramBotService telegramBotService, Handlers handlers) {
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
    public void acceptChoseShelterComand(UserChat user, Commands text, long chatId){
         if(text.equals("/dog") || text.equals("/cat") && user.getBotState() == BotState.CHOOSE_SHELTER){
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

    private final void shelterType(UserChat user, Commands text, long chatId) {
        if (text.equals(Commands.DOG)) {
            user.setBotState(BotState.MENU);
            user.setCurrentChosenShelter(ShelterType.DOG_SHELTER);
            user.setHasChosenShelter(true);
            handlers.handleShelterConsultation(chatId, text.toString());
        } else if (text.equals(Commands.CAT)) {
            user.setBotState(BotState.MENU);
            user.setCurrentChosenShelter(ShelterType.CAT_SHELTER);
            user.setHasChosenShelter(true);
            handlers.handleShelterConsultation(chatId, text.toString());
        }
    }
}
