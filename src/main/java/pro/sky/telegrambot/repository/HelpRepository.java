package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.entity.Help;

/**
 * repository for data access appeals
 */
public interface HelpRepository extends JpaRepository<Help, Long> {
}
