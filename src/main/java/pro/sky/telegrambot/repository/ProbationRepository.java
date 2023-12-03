package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;

import java.util.List;

public interface ProbationRepository extends JpaRepository<Probation, Long> {

    List<Probation> findAllByUserChat(UserChat userChat);

    Probation findByPet(Pet pet);

    List<Probation> findAllByVolunteer(Volunteer volunteer);

    Probation findFirstByVolunteerAndStatus(Volunteer volunteer, String status);
}
