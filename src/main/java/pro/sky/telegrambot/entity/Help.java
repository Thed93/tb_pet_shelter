package pro.sky.telegrambot.entity;

import javax.persistence.*;

/**
 * class of user's appealing to volunteer with text
 */
@Entity
@Table(name = "help")
public class Help {

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

    /**
     * user's appeal
     */
    @Column(length = 2000, nullable = true)
    private String text;

    public Help(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }
}