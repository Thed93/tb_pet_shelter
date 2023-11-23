package pro.sky.telegrambot.entity;

import com.pengrad.telegrambot.model.PhotoSize;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    private String text;

/*    *//**
     * number of report, that this user send
     *//*
    @Column(name = "report_number", nullable = false)
    private int reportNumber;*/

    private String status;

    public PetReport(UserChat user, LocalDateTime dateTime, String photoPath, String text, String status) {
        this.user = user;
        this.dateTime = dateTime;
        this.photoPath = photoPath;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

/*    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }*/

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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