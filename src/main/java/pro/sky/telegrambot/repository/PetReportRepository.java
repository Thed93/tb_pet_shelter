package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;

import java.util.Optional;

/**
 * repository for data access to reports
 */
public interface PetReportRepository extends JpaRepository<PetReport, Long> {

    /**
     * method to find user by Name and Surname
     *
     * @param Name
     * @param Surname
     * @return
     */
    //List<PetReport> findReportsByUserNameAndUserSurname(String Name, String Surname);

    @Query(value = "SELECT * FROM pet_report WHERE user_id = :userId ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<PetReport> findLastPetReportByUserId(@Param("userId") Long userId);


    PetReport findPetReportByPetAndStatus(Pet pet, String status);

    PetReport findPetReportByUserChatAndStatus(UserChat userChat, String status);

    PetReport findFirstPetReportByVolunteerAndStatus(Volunteer volunteer, String state);
}