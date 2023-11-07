package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.repository.AppealToVolunteerRepository;
import pro.sky.telegrambot.repository.HelpRepository;

import javax.transaction.Transactional;
import java.util.Collection;

/**
 * service for processing commands
 */
@Service
public class HelpService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PetReportService.class);

    /**
     * repository for data access
     */
    private final HelpRepository helpRepository;


    /**
     *
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;


    public HelpService(AppealToVolunteerRepository appealToVolunteerRepository, HelpRepository helpRepository, TelegramBotService telegramBotService) {
        this.helpRepository = helpRepository;
        this.telegramBotService = telegramBotService;
    }

    /**
     *
     * saving help appealing with message to volunteers
     * <br>
     * use repository method {@link org.springframework.data.jpa.repository.JpaRepository#save(Object)}
     *
     * @param help building in {@link pro.sky.telegrambot.listener.TelegramBotUpdatesListener}
     * @return saving help appealing
     */
    @Transactional
    public Help saveHelpAppeal (Help help){
        return helpRepository.save(help);
    }

    /**
     * return all saving help appeals
     * <br>
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all saving help appeals
     */
    public Collection<Help> getAllHelps(){
        return helpRepository.findAll();
    }
}