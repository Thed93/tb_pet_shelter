package pro.sky.telegrambot.entity;

import javax.persistence.*;


/**
 * class with pet and its owner after the end of the probation
 */
@Entity
public class Adopted {

    /**
     * id of adopted
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * user who owns the animal
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

    /**
     * pet owned by the user
     */
    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public UserChat getUserChat() {
        return userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
