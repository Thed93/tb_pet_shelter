package pro.sky.telegrambot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * class of users reports
 */
@Entity
@Table(name = "pet_report")
public class PetReport {

    /**
     * unique identifier of report
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

    /**
     * date, when user send report
     */
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    /**
     * photo, that user send
     */
    @Column(nullable = true)
    private String photoPath;

    /**
     * user's report
     */
    @Column(length = 2000, nullable = true)
    private String diet;

    @Column(name = "well_being", length = 2000, nullable = true)
    private String wellBeing;

    @Column(name = "change_in_behavior", length = 2000, nullable = true)
    private String changeInBehavior;

    /**
     * number of report, that this user send
     *//*
    @Column(name = "report_number", nullable = false)
    private int reportNumber;*/
    private String status;

    public PetReport(UserChat user, LocalDateTime dateTime, String photoPath, String diet, String wellBeing, String changeInBehavior, String status) {
        this.user = user;
        this.dateTime = dateTime;
        this.photoPath = photoPath;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.changeInBehavior = changeInBehavior;
        this.status = status;
    }

    public PetReport() {
    }

    public UserChat getUser() {
        return user;
    }

    public void setUser(UserChat user) {
        this.user = user;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public void setChangeInBehavior(String changeInBehavior) {
        this.changeInBehavior = changeInBehavior;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }

    public String getChangeInBehavior() {
        return changeInBehavior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return Objects.equals(id, petReport.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}