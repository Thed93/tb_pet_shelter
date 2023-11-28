package pro.sky.telegrambot.enums;


import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;

/**
 * item, that show, where is user now
 */
public enum BotState {
    START("/start"),
    CHOOSE_SHELTER("/choose_shelter"),
    MENU("/menu"),
    INFO("/info"),
    ADOPTION("/adoption"),
    REPORT("/report"),
    WAITING_FOR_DIET("/diet"),
    WAITING_FOR_WELL_BEING("/well_being"),
    WAITING_FOR_CHANGE_IN_BEHAVIOR("/change_in_behavior"),
    VOLUNTEER("/volunteer"),
    HELP("/help");

    private final String state;

    BotState(String commandText) {
        this.state = commandText;
    }
}