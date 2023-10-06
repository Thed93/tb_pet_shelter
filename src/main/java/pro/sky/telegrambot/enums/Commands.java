package pro.sky.telegrambot.enums;

import pro.sky.telegrambot.entity.Help;

public enum Commands {
    START("/start"),
    CAT("/cat"),
    DOG("/dog"),
    INFORMATION("/information"),
    ADOPTION("/adoption"),
    REPORT("/report"),
    WELCOME_RULES("/welcomeRules"),
    DOCS("/docs"),
    PET_TRANSPORTATION("/petTransportation"),
    BABY_PET_HOUSE("/babyPetHouse"),
    PET_HOUSE("/petHouse"),
    SPECIAL_PET_HOUSE("/specialPetHouse"),
    ADVICE_DOG_HANDLER("/adviceDogHandler"),
    DOG_HANDLER("/dogHandler"),
    REFUSE_PET("/refusePet"),
    VOLUNTEER("/volunteer"),
    ABOUT("/about"),
    WORKING_HOURS("/workingHours"),
    SECURITY_NUMBER("/securityNumber"),
    SAFETY_PRECAOTIONS("/safetyPrecautions"),
    HELP("/help");


    private final String commandText;

    Commands(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }
}
