package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.AppealToVolonteer;

public interface AppealToVolunteerRepository extends JpaRepository<AppealToVolonteer, Long> {
}
