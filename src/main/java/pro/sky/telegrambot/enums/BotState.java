package pro.sky.telegrambot.enums;


import org.springframework.stereotype.Component;

/**
 * item, that show, where is user now
 */
@Component
public enum BotState {
    START,
    CHOOSE_SHELTER,
    MENU,
    INFO,
    ADOPTION,
    REPORT,
    VOLUNTEER,
    HELP
}

