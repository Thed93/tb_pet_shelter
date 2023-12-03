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
     * pet associated with this appeal
     */
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    /**
     * user who created the report
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserChat userChat;

    /**
     * date, when user send report
     */
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    /**
     * path to the photo, that user send
     */
    @Column(name = "photo_path")
    private String photoPath;

    /**
     * pet diet
     */
    @Column(length = 2000)
    private String diet;

    /**
     * pet's well-being
     */
    @Column(name = "well_being", length = 2000)
    private String wellBeing;

    /**
     * change in pet behavior
     */
    @Column(name = "change_in_behavior", length = 2000)
    private String changeInBehavior;

    /**
     * status of the report which shows what stage it is at
     */
    private String status;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer volunteer;

    public PetReport(Long id, Pet pet, UserChat userChat, LocalDateTime dateTime, String photoPath, String diet, String wellBeing, String changeInBehavior, String status) {
        this.id = id;
        this.pet = pet;
        this.userChat = userChat;
        this.dateTime = dateTime;
        this.photoPath = photoPath;
        this.diet = diet;
        this.wellBeing = wellBeing;
        this.changeInBehavior = changeInBehavior;
        this.status = status;
    }

    public PetReport() {
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public UserChat getUserChat() {
        return userChat;
    }

    public void setUserChat(UserChat userChat) {
        this.userChat = userChat;
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
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