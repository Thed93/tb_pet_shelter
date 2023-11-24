package pro.sky.telegrambot.enums;

import org.springframework.stereotype.Component;

/**
 * item, that show, what kind of shelter user chose last time
 */
public enum ShelterType {
    DOG_SHELTER("/dog"),
    CAT_SHELTER("/cat");

    private final String command;

    ShelterType(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}