package pro.sky.telegrambot.enums;


import org.springframework.stereotype.Component;

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
    VOLUNTEER("/volunteer"),
    HELP("/help");

    private final String state;

    BotState(String commandText) {
        this.state = commandText;
    }
}