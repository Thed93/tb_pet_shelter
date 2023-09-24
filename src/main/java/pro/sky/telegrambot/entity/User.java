package pro.sky.telegrambot.entity;

import pro.sky.telegrambot.listener.ShelterType;

import javax.persistence.*;


@Entity
@Table(name = "pet_report")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = true)
    private String surname;

    @Column(length = 20, nullable = true)
    private ShelterType currentChosenShelter;

    @Column
    private boolean hasChoosenShelter = false;


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

    public boolean isHasChoosenShelter() {
        return hasChoosenShelter;
    }

    public void setHasChoosenShelter(boolean hasChoosenShelter) {
        this.hasChoosenShelter = hasChoosenShelter;
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

}

