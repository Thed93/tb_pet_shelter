package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.entity.User;

/**
 * repository for data access to users
 */
public interface UserRepository extends JpaRepository<User, Long> {


   public User findUserByNameAndSurname(String name, String surname);
}
