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
    private UserChat user;

    /**
     * user's appeal
     */
    @Column(length = 2000, nullable = true)
    private String text;

    public Help(UserChat user, String text) {
        this.user = user;
        this.text = text;
    }

    public Help() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserChat getUser() {
        return user;
    }

    public void setUser(UserChat user) {
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
