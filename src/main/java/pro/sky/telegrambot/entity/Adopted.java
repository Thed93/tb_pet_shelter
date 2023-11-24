package pro.sky.telegrambot.entity;

import javax.persistence.*;

@Entity
public class Adopted {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
