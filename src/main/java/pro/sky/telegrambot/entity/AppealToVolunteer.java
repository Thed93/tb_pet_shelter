package pro.sky.telegrambot.entity;

import javax.persistence.*;

/**
 * class of user's appealing to volunteer
 */
@Entity
@Table(name = "appeal_to_volunteer")
public class AppealToVolunteer {

    /**
     * unique identifier of appealing
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * user associated with this appeal
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat user;


    public AppealToVolunteer(UserChat user) {
        this.user = user;
    }

    public AppealToVolunteer() {
    }

    public UserChat getUser() {
        return user;
    }

    public void setUser(UserChat user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}