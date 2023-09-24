package pro.sky.telegrambot.entity;

import com.pengrad.telegrambot.model.PhotoSize;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "pet_report")
public class PetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String owner;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 20, nullable = true, columnDefinition = "oid")
    private PhotoSize[] photo;

    @Column(length = 2000, nullable = true)
    private String text;

    @Column(name = "report_number", nullable = false)
    private int reportNumber;

    @ManyToOne
    private User user;


    public PetReport(String owner, LocalDateTime dateTime, PhotoSize[] photo, String text) {
        this.owner = owner;
        this.dateTime = dateTime;
        this.photo = photo;
        this.text = text;
    }

    public PetReport() {
    }

    public int getReportNumber() {
        return reportNumber;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
