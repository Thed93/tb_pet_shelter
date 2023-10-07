package pro.sky.telegrambot.entity;

import com.pengrad.telegrambot.model.PhotoSize;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private User user;

    /**
     * date, when user send report
     */
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    /**
     * photo, that user send
     */
    @Column(length = 20, nullable = true, columnDefinition = "oid")
    private PhotoSize[] photo;

    /**
     * user's report
     */
    @Column(length = 2000, nullable = true)
    private String text;

    /**
     * number of report, that this user send
     */
    @Column(name = "report_number", nullable = false)
    private int reportNumber;

    @Column(name = "chat_id", nullable = false)
    private long chatId;


    public PetReport(User user, LocalDateTime dateTime, PhotoSize[] photo, String text, long chatId) {
        this.user = user;
        this.dateTime = dateTime;
        this.photo = photo;
        this.text = text;
        this.chatId = chatId;
    }

    public PetReport() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
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

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public Long getId() {
        return id;
    }
}


