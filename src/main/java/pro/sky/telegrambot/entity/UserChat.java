package pro.sky.telegrambot.entity;
import javax.persistence.*;
import java.util.Objects;


/**
 * class of users
 */
@Entity
@Table(name = "\"user\"")
public class UserChat {

    /**
     * unique identifier of user
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    private String currentChosenShelter;

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
    private String botState;

    public UserChat(Long userId, String name, String surname, String currentChosenShelter, boolean hasChosenShelter, String botState) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.currentChosenShelter = currentChosenShelter;
        this.hasChosenShelter = hasChosenShelter;
        this.botState = botState;
    }

    public UserChat(Long userId, String name, String surname) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCurrentChosenShelter() {
        return currentChosenShelter;
    }

    public void setCurrentChosenShelter(String currentChosenShelter) {
        this.currentChosenShelter = currentChosenShelter;
    }

    public boolean isHasChosenShelter() {
        return hasChosenShelter;
    }

    public void setHasChosenShelter(boolean hasChosenShelter) {
        this.hasChosenShelter = hasChosenShelter;
    }

    public String getBotState() {
        return botState;
    }

    public void setBotState(String botState) {
        this.botState = botState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserChat userChat = (UserChat) o;
        return hasChosenShelter == userChat.hasChosenShelter && Objects.equals(userId, userChat.userId) && Objects.equals(name, userChat.name) && Objects.equals(surname, userChat.surname) && Objects.equals(currentChosenShelter, userChat.currentChosenShelter) && Objects.equals(botState, userChat.botState);
    }

    public UserChat() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, surname, currentChosenShelter, hasChosenShelter, botState);
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", currentChosenShelter='" + currentChosenShelter + '\'' +
                ", hasChosenShelter=" + hasChosenShelter +
                ", botState='" + botState + '\'' +
                '}';
    }
}