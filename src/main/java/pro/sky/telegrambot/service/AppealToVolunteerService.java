package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.repository.AppealToVolunteerRepository;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * service for processing commands
 */
@Service
public class AppealToVolunteerService {


    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetReportService.class);

    /**
     * repository for data access
     */
    private final AppealToVolunteerRepository appealToVolunteerRepository;


    /**
     *
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    public AppealToVolunteerService(AppealToVolunteerRepository appealToVolunteerRepository, TelegramBotService telegramBotService) {
        this.appealToVolunteerRepository = appealToVolunteerRepository;
        this.telegramBotService = telegramBotService;
    }

    /**
     *
     * saving appealing for volunteers
     * <br>
     * use repository method {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param appealToVolonteer building in:
     * <br>{@link pro.sky.telegrambot.handle.Handlers#volunteer(User, long)}  }
     *
     * @return saving appeal
     */
    @Transactional
    public AppealToVolonteer saveAppeal (AppealToVolonteer appealToVolonteer){
        return appealToVolunteerRepository.save(appealToVolonteer);
    }

    /**
     *
     * return all saving appeals
     * <br>
     * use repository method {@link JpaRepository#findAll()}
     * @return all saving appeals
     */
    public Collection<AppealToVolonteer> getAllAppeals(){
        return appealToVolunteerRepository.findAll();
    }
}
