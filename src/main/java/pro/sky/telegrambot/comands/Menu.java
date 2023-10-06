package pro.sky.telegrambot.comands;

import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

/**
 * class for processing user's message
 */
public class Menu {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public Menu(TelegramBotService telegramBotService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
    }

    /**
     *
     * redirection and send parameters for user depending on his message
     *
     * @param user
     * @param text user's message
     * @param chatId
     */
    public void acceptInfoCommands(User user, Commands text, Long chatId) {
        user.setBotState(BotState.INFO);
        if (text.equals(Commands.INFORMATION)) {
            user.setBotState(BotState.INFO);
            infoMenu(chatId, user);
        }
        if (text.equals(Commands.ADOPTION)) {
            user.setBotState(BotState.ADOPTION);
            adoptionMenu(chatId, user);
        }
        if (text.equals(Commands.REPORT)) {
            user.setBotState(BotState.REPORT);
            reportMenu(user, text.toString(), chatId);
        }
        if (text.equals(Commands.VOLUNTEER)) {
            user.setBotState(BotState.VOLUNTEER);
            volunteer(user, chatId);
        }
    }


    /**
     * method, if user send {@code "/information" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#handleAdoptionConsultation(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void infoMenu (Long chatId, User user){
        handlers.handleAdoptionConsultation(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/adoption" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#howToTakePet(Long, ShelterType)}
     *
     * @param chatId
     * @param user
     */
    private final void adoptionMenu (Long chatId, User user){
        handlers.howToTakePet(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/report" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#reportMenu(User, String, long)}
     *
     * @param user
     * @param text
     * @param chatId
     */
    private void reportMenu(User user, String text, long chatId){
        handlers.reportMenu(user, text, chatId);
    }

    /**
     * method, if user send {@code "/volunteer" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#volunteer(User, long)}
     *
     * @param user
     * @param chatId
     */
    private void volunteer(User user, long chatId){
        handlers.volunteer(user, chatId);
    }
}
