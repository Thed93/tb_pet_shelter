package pro.sky.telegrambot.enums;


import org.springframework.stereotype.Component;

/**
 * item, that show, where is user now
 */
@Component
public enum BotState {
    START("START"),
    CHOOSE_SHELTER("CHOOSE_SHELTER"),
    MENU("MENU"),
    INFO("INFO"),
    ADOPTION("ADOPTION"),
    REPORT("REPORT"),
    VOLUNTEER("VOLUNTEER"),
    HELP("HELP");

    private final String state;

    BotState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}

