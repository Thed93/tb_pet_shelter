package pro.sky.telegrambot.comands;

import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

/**
 * class for processing user's message
 */
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
    public void acceptChoseShelterComand(User user, String text, long chatId){
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

    private final void shelterType(User user, String text, long chatId) {
        if (text.equals("/dog")) {
            user.setBotState(BotState.MENU);
            user.setCurrentChosenShelter(ShelterType.DOG_SHELTER);
            user.setHasChosenShelter(true);
            handlers.handleShelterConsultation(chatId, text);
        } else if (text.equals("/cat")) {
            user.setBotState(BotState.MENU);
            user.setCurrentChosenShelter(ShelterType.CAT_SHELTER);
            user.setHasChosenShelter(true);
            handlers.handleShelterConsultation(chatId, text);
        }
    }
}
