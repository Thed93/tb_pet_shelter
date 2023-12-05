package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
