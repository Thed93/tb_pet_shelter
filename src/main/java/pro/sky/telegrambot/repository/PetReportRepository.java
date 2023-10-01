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
     * method to find user by name and surname
     *
     * @param name
     * @param surname
     * @return
     */
    List<PetReport> findReportsByNameAndSurname(String name, String surname);
}
