package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.PetReportService;
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
    private final PetReportService petReportService;

    /**
     * class for getting methods
     */
    private final Handlers handlers;

    public Menu(TelegramBotService telegramBotService, UserChatService userChatService, PetReportService petReportService, Handlers handlers) {
        this.telegramBotService = telegramBotService;
        this.userChatService = userChatService;
        this.petReportService = petReportService;
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
        Commands currentCommand = Commands.valueOf(text.substring(1).toUpperCase());
        switch (currentCommand){
            case INFORMATION:
                userChatService.setInfo(chatId);
                infoMenu(chatId);
                break;
            case ADOPTION:
                userChatService.setAdoption(chatId);
                adoptionMenu(chatId);
                break;
            case REPORT:
/*                userChatService.setReport(chatId);
                reportMenu(text, chatId);*/
                //petReportService.report(chatId);
                petReportService.choicePet(chatId);
                break;
            case VOLUNTEER:
                userChatService.setVolunteer(chatId);
                volunteer(chatId);
                break;
            case BACK:
                break;
            default:
                telegramBotService.sendMessage(chatId, "Неправильная команда\n" +
                        "для возврата в начало нажмите - " + Commands.START.getCommandText() + "\n" +
                        "для возврата в предыдущее меню нажмите - " + Commands.BACK.getCommandText());
                break;
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
        handlers.reportMenu(chatId);
    }

    /**
     * method, if user send {@code "/volunteer" }
     * <br>
     * use method
     *
     * @param chatId
     */
    private void volunteer(long chatId){
        handlers.volunteer(chatId);
    }

}