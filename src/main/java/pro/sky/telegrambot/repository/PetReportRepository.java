package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.PetReport;

import java.util.Collection;
import java.util.List;

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
    List<PetReport> findReportsByNameAndSurname(String Name, String Surname);
}
