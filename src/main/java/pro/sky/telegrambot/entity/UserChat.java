package pro.sky.telegrambot.entity;

import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;

import javax.persistence.*;


/**
 * class of users
 */
@Entity
@Table(name = "user")
public class UserChat {

    /**
     * unique identifier of user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
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
    @Column(name = "current_chosen_shelter")
    @Enumerated(EnumType.STRING)
    private ShelterType currentChosenShelter;

    /**
     * flag, that show, user chose shelter or not
     */
    @Column(name = "has_chosen_shelter")
    private boolean hasChosenShelter = false;

    /**
     * menu item, where user now
     * {@link pro.sky.telegrambot.enums.BotState}
     */
    @Column(name = "bot_state")
    @Enumerated(EnumType.STRING)
    private BotState botState;


    public UserChat(String name, String surname, ShelterType currentChosenShelter, boolean hasChosenShelter, BotState botState) {
        this.name = name;
        this.surname = surname;
        this.currentChosenShelter = currentChosenShelter;
        this.hasChosenShelter = hasChosenShelter;
        this.botState = botState;
    }


    public UserChat() {
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

