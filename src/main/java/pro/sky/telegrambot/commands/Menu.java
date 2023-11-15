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
     * @param text user's message
     * @param chatId
     */
    public void acceptInfoCommands(String text, Long chatId) {
        if (text.equals(Commands.INFORMATION.getCommandText())) {
            userChatService.setInfo(chatId);
            infoMenu(chatId);
        }
        if (text.equals(Commands.ADOPTION.getCommandText())) {
            userChatService.setAdoption(chatId);
            adoptionMenu(chatId);
        }
        if (text.equals(Commands.REPORT.getCommandText())) {
            userChatService.setReport(chatId);
            reportMenu(text, chatId);
        }
        if (text.equals(Commands.VOLUNTEER.getCommandText())) {
            userChatService.setVolunteer(chatId);
            volunteer(chatId);
        }
    }


    /**
     * method, if user send {@code "/information" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#handleAdoptionConsultation(Long, String)}
     *
     * @param chatId
     */
    private final void infoMenu (Long chatId){
        handlers.handleAdoptionConsultation(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/adoption" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#howToTakePet(Long, String)}
     *
     * @param chatId
     */
    private final void adoptionMenu (Long chatId){
        handlers.howToTakePet(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/report" }
     * <br>
     * use method
     *
     * @param text
     * @param chatId
     */
    private void reportMenu(String text, long chatId){
        handlers.reportMenu(text, chatId);
    }

    /**
     * method, if user send {@code "/volunteer" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#volunteer(UserChat, long)}
     *
     * @param chatId
     */
    private void volunteer(long chatId){
        handlers.volunteer(chatId);
    }
}