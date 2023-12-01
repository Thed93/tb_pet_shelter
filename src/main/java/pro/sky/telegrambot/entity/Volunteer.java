package pro.sky.telegrambot.entity;

import javax.persistence.*;

@Entity
@Table(name = "volunteers")
public class Volunteer {

    @Id
    private Long id;

    private String name;

    @Column(name = "count_users")
    private int countUsers;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserChat user;

    private String state;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(int countUsers) {
        this.countUsers = countUsers;
    }

    public UserChat getUser() {
        return user;
    }

    public void setUser(UserChat user) {
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
