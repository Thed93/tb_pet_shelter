package pro.sky.telegrambot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

@Component
public class Back {
    private static final Logger LOG = LoggerFactory.getLogger(Back.class);

    private final UserChatService userChatService;

    private final TelegramBotService telegramBotService;

    private final Handlers handlers;

    public Back(UserChatService userChatService, TelegramBotService telegramBotService, Handlers handlers) {
        this.userChatService = userChatService;
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
    }

    public void goBack(long chatId, String text){
        switch (userChatService.getUserChatStatus(chatId)){
            case INFO:
            case ADOPTION:
                userChatService.setMenu(chatId);
                telegramBotService.sendMessage(chatId, "Вы перешли в меню");
                handlers.handleShelterConsultation(chatId, userChatService.getShelter(chatId));
                break;
            case MENU:
            case CHOOSE_SHELTER:
                userChatService.setStartState(chatId);
                telegramBotService.sendMessage(chatId, "Вы перешли к началу");
                break;
            default:
                telegramBotService.sendMessage(chatId, "Суета");
                break;

        }
    }
}
