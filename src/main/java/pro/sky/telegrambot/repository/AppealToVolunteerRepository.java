package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.AppealToVolunteer;

public interface AppealToVolunteerRepository extends JpaRepository<AppealToVolunteer, Long> {
}