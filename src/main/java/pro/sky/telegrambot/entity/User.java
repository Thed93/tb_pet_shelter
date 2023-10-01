package pro.sky.telegrambot.entity;

import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;

import javax.persistence.*;


/**
 * class of users
 */
@Entity
@Table(name = "user")
public class User {

    /**
     * unique identifier of user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * user's name
     */
    @Column(length = 20, nullable = false)
    private String name;

    /**
     * user's surname
     */
    @Column(length = 20, nullable = true)
    private String surname;

    /**
     * type of shelter, that user chose
     * {@link pro.sky.telegrambot.enums.ShelterType}
     */
    @Enumerated(EnumType.STRING)
    private ShelterType currentChosenShelter;

    /**
     * flag, that show, user chose shelter or not
     */
    @Column
    private boolean hasChosenShelter = false;

    /**
     * menu item, where user now
     * {@link pro.sky.telegrambot.enums.BotState}
     */
    @Enumerated(EnumType.STRING)
    private BotState botState = BotState.START;


    public User(String name, String surname, ShelterType currentChosenShelter, boolean hasChosenShelter, BotState botState) {
        this.name = name;
        this.surname = surname;
        this.currentChosenShelter = currentChosenShelter;
        this.hasChosenShelter = hasChosenShelter;
        this.botState = botState;
    }


    public User() {
    }


    public ShelterType getCurrentChosenShelter() {
            return currentChosenShelter;

    }

    public void setCurrentChosenShelter(ShelterType currentChosenShelter) {
        this.currentChosenShelter = currentChosenShelter;
    }

    public boolean isHasChosenShelter() {
        return !hasChosenShelter;
    }

    public void setHasChosenShelter(boolean hasChosenShelter) {
        this.hasChosenShelter = hasChosenShelter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
    }
}

