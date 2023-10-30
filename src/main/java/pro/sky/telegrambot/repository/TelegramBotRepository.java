package pro.sky.telegrambot.repository;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramBotRepository extends JpaRepository<TelegramBot, Long> {
}
