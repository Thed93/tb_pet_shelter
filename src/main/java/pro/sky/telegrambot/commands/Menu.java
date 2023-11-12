package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

/**
 * class for processing user's message
 */
@Component
public class Menu {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;
    private final UserChatService userChatService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public Menu(TelegramBotService telegramBotService, UserChatService userChatService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.userChatService = userChatService;
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
    public void acceptInfoCommands(UserChat user, String text, Long chatId) {
        user.setBotState(BotState.INFO.toString());
        if (text.equals(Commands.INFORMATION.getCommandText())) {
            user.setBotState(BotState.INFO.toString());
            userChatService.saveUser(user);
            infoMenu(chatId, user);
        }
        if (text.equals(Commands.ADOPTION.getCommandText())) {
            user.setBotState(BotState.ADOPTION.toString());
            userChatService.saveUser(user);
            adoptionMenu(chatId, user);
        }
        if (text.equals(Commands.REPORT.getCommandText())) {
            user.setBotState(BotState.REPORT.toString());
            userChatService.saveUser(user);
            reportMenu(user, text, chatId);
        }
        if (text.equals(Commands.VOLUNTEER.getCommandText())) {
            user.setBotState(BotState.VOLUNTEER.toString());
            userChatService.saveUser(user);
            volunteer(user, chatId);
        }
    }


    /**
     * method, if user send {@code "/information" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#handleAdoptionConsultation(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void infoMenu (Long chatId, UserChat user){
        handlers.handleAdoptionConsultation(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/adoption" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#howToTakePet(Long, String)}
     *
     * @param chatId
     * @param user
     */
    private final void adoptionMenu (Long chatId, UserChat user){
        handlers.howToTakePet(chatId, user.getCurrentChosenShelter());
    }

    /**
     * method, if user send {@code "/report" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#reportMenu(UserChat, String, long)}
     *
     * @param user
     * @param text
     * @param chatId
     */
    private void reportMenu(UserChat user, String text, long chatId){
        handlers.reportMenu(user, text, chatId);
    }

    /**
     * method, if user send {@code "/volunteer" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#volunteer(UserChat, long)}
     *
     * @param user
     * @param chatId
     */
    private void volunteer(UserChat user, long chatId){
        handlers.volunteer(user, chatId);
    }
}