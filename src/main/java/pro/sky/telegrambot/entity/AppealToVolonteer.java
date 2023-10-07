package pro.sky.telegrambot.entity;

import javax.persistence.*;

/**
 * class of user's appealing to volunteer
 */
@Entity
@Table(name = "appeal_to_volonteer")
public class AppealToVolonteer {

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
    @JoinColumn(name = "userId")
    private User user;


    public AppealToVolonteer(User user) {
        this.user = user;
    }

    public AppealToVolonteer() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
