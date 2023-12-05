package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Adopted;

public interface AdoptedRepository extends JpaRepository<Adopted, Long> {
}
