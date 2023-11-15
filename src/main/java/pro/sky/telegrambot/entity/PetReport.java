package pro.sky.telegrambot.entity;

import com.pengrad.telegrambot.model.PhotoSize;

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
    @Column(length = 20, nullable = true, columnDefinition = "bytea")
    private PhotoSize[] photo;

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
     */
    @Column(name = "report_number", nullable = false)
    private int reportNumber;

    public PetReport(UserChat user, LocalDateTime dateTime, PhotoSize[] photo, String diet, String wellBeing, String changeInBehavior) {
        this.user = user;
        this.dateTime = dateTime;
        this.photo = photo;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.changeInBehavior = changeInBehavior;
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PhotoSize[] getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoSize[] photo) {
        this.photo = photo;
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

    public void setChangeInBehavior(String changeInBehavior) {
        this.changeInBehavior = changeInBehavior;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetReport petReport = (PetReport) o;
        return reportNumber == petReport.reportNumber && Objects.equals(id, petReport.id) && Objects.equals(user, petReport.user) && Objects.equals(dateTime, petReport.dateTime) && Arrays.equals(photo, petReport.photo) && Objects.equals(diet, petReport.diet) && Objects.equals(wellBeing, petReport.wellBeing) && Objects.equals(changeInBehavior, petReport.changeInBehavior);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, user, dateTime, diet, wellBeing, changeInBehavior, reportNumber);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }
}