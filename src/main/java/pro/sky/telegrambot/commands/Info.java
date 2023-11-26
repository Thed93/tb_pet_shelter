package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;


/**
 * class for processing user's message
 */
@Component
public class Info {

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;


    /**
     * class for getting methods
     */
    private final Handlers handlers;

    private final UserChatService userChatService;

    public Info(TelegramBotService telegramBotService, Handlers handlers, UserChatService userChatService) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
        this.userChatService = userChatService;
    }

    /**
     * redirection user depending on his message
     *
     * @param text   user's message
     * @param chatId
     */
    public void acceptInfoCommands(String text, Long chatId) {

        // Пока что пускай так, небыло времени что-то лучше придумать и здесь нет команды /writeData
        // хотя бот предлагает эту команду

        Commands currentCommand = Commands.valueOf(toConstantStyle(text.substring(1)));
        switch (currentCommand) {
            case ABOUT:
                getShelterInfo(chatId);
                break;
            case WORKING_HOURS:
                workingHours(chatId);
                break;
            case SECURITY_NUMBER:
                securityNumber(chatId);
                break;
            case SAFETY_PRECAUTIONS:
                safetyPrecautions(chatId);
                break;
            case HELP:
                help(chatId);
                break;
            case BACK:
                break;
            default:
                telegramBotService.sendMessage(chatId, "Неправильная команда\n" +
                        "для возврата в начало нажмите - " + Commands.START.getCommandText() + "\n" +
                        "для возврата в предыдущее меню нажмите - " + Commands.BACK.getCommandText());;
                        break;
        }
    }

    private final String toConstantStyle(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) >= 65 && string.charAt(i) <= 90) {
                return (string.substring(0, i) + '_' + string.substring(i)).toUpperCase();
            }
        }
        return string.toUpperCase();
    }


    /**
     * method, if user send {@code "/about" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#aboutShelter(Long, String)}
     *
     * @param chatId
     */
    private final void getShelterInfo(Long chatId) {
        handlers.aboutShelter(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/workingHours" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#workingHours(Long, String)}
     *
     * @param chatId
     */
    private final void workingHours(Long chatId) {
        handlers.workingHours(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/securityNumber" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#securityNumber(Long, String)}
     *
     * @param chatId
     */
    private final void securityNumber(Long chatId) {
        handlers.securityNumber(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/safetyPrecautions" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#safetyPrecautions(Long, String)}
     *
     * @param chatId
     */
    private final void safetyPrecautions(Long chatId) {
        handlers.safetyPrecautions(chatId, userChatService.getShelter(chatId));
    }

    /**
     * method, if user send {@code "/help" }
     * <br>
     * use method {@link pro.sky.telegrambot.handle.Handlers#writeData(Long)}
     *
     * @param chatId
     */
    private final void help(Long chatId) {
        handlers.writeData(chatId);
    }
}