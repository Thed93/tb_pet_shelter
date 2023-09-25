package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.PetReport;

import java.util.Collection;
import java.util.List;

public interface PetReportRepository extends JpaRepository<PetReport, Long> {
    List<PetReport> findReportsByNameAndSurname(String name, String surname);
}
