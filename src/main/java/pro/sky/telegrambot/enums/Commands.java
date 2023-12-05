package pro.sky.telegrambot.enums;

public enum Commands {
    START("/start"),
    CAT("/cat"),
    DOG("/dog"),
    INFORMATION("/information"),
    ADOPTION("/adoption"),
    REPORT("/report"),
    WELCOME_RULES("/welcome_rules"),
    DOCS("/docs"),
    PET_TRANSPORTATION("/pet_transportation"),
    BABY_PET_HOUSE("/baby_pet_house"),
    PET_HOUSE("/pet_house"),
    SPECIAL_PET_HOUSE("/special_pet_house"),
    ADVICE_DOG_HANDLER("/advice_dog_handler"),
    DOG_HANDLER("/dog_handler"),
    REFUSE_PET("/refuse_pet"),
    VOLUNTEER("/volunteer"),
    ABOUT("/about"),
    WORKING_HOURS("/working_hours"),
    SECURITY_NUMBER("/security_number"),
    SAFETY_PRECAUTIONS("/safety_precautions"),
    HELP("/help"),
    BACK("/back");


    private final String commandText;

    Commands(String commandText) {
        this.commandText = commandText;
    }

    public String getCommandText() {
        return commandText;
    }
}