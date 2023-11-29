package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Probation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

    @OneToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @Column(name = "probation_end_date")
    private LocalDateTime probationEndDate;

    @Column(name = "last_report_date")
    private LocalDateTime lastReportDate;

    public Long getId() {
        return id;
    }

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

    public LocalDateTime getProbationEndDate() {
        return probationEndDate;
    }

    public void setProbationEndDate(LocalDateTime probationEndDate) {
        this.probationEndDate = probationEndDate;
    }

    public LocalDateTime getLastReportDate() {
        return lastReportDate;
    }

    public void setLastReportDate(LocalDateTime lastReportDate) {
        this.lastReportDate = lastReportDate;
    }
}
