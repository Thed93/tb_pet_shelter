package pro.sky.telegrambot.enums;


import org.springframework.stereotype.Component;
import pro.sky.telegrambot.entity.UserChat;

/**
 * item, that show, where is user now
 */
public enum BotState {
    START,
    CHOOSE_SHELTER,
    MENU,
    INFO,
    ADOPTION,
    CHOOSE_PET,
    REPORT,
    VOLUNTEER,
    HELP;

}