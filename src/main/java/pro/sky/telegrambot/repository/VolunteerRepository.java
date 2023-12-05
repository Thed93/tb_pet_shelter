package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Volunteer findByUser(UserChat userChat);
}
