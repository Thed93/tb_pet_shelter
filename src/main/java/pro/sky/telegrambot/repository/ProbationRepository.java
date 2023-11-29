package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.entity.UserChat;

import java.util.List;

public interface ProbationRepository extends JpaRepository<Probation, Long> {

    List<Probation> findAllByUserChat(UserChat userChat);
}
