package pro.sky.telegrambot.entity;

import pro.sky.telegrambot.listener.BotState;
import pro.sky.telegrambot.listener.ShelterType;

import javax.persistence.*;


@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = true)
    private String surname;

    private ShelterType currentChosenShelter;

    private boolean hasChosenShelter = false;

    private BotState botState;


    public User(String name, String surname) {
        this.name = name;
        this.surname = surname;
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

