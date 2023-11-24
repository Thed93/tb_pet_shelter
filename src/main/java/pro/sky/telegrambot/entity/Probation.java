package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Probation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private LocalDate probationEndDate;
}
