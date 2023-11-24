package pro.sky.telegrambot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.UserChatService;

@Component
public class Back {
    private static final Logger LOG = LoggerFactory.getLogger(Back.class);

    private final UserChatService userChatService;

    private final Handlers handlers;

    public Back(UserChatService userChatService, Handlers handlers) {
        this.userChatService = userChatService;
        this.handlers = handlers;
    }

    public void goBack(long chatId){

        BotState currentState = userChatService.getUserChatStatus(chatId);
        switch (currentState){
            case INFO:
            case ADOPTION:
                userChatService.setMenu(chatId);
                break;
            case MENU:
                userChatService.setChoseShelter(chatId);
                break;
            case CHOOSE_SHELTER:
                userChatService.setStartState(chatId);
                break;
        }
    }
}
