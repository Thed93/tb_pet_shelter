package pro.sky.telegrambot.entity;

import javax.persistence.*;

@Entity
public class Adopted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

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
