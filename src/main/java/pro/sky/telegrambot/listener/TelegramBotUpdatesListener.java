package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.comands.*;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.repository.UserRepository;
import pro.sky.telegrambot.service.HelpService;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.TelegramBotService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Telegram bot update listener.
 */
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {


    /**
     * class for adding message to programmer
     */
    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    /**
     * user, that appealed to Telegram - bot
     */
    private User user;

    /**
     * copy of Telegram - bot for sending message
     */
    private final TelegramBotService telegramBotService;

    private final Start start;

    private final ChoseShelter choseShelter;


    private final TelegramBot telegramBot;

    private final UserRepository userRepository;

    private final Menu menu;

    private final Info info;

    private final Adoption adoption;

    private final HelpService helpService;


    private final PetReportService petReportService;



    public TelegramBotUpdatesListener(TelegramBotService telegramBotService, Start start, ChoseShelter choseShelter, TelegramBot telegramBot, UserRepository userRepository, Menu menu, Info info, Adoption adoption, HelpService helpService, PetReportService petReportService) {
        this.telegramBotService = telegramBotService;
        this.start = start;
        this.choseShelter = choseShelter;
        this.telegramBot = telegramBot;
        this.userRepository = userRepository;
        this.menu = menu;
        this.info = info;
        this.adoption = adoption;
        this.helpService = helpService;
        this.petReportService = petReportService;
    }

    /**
     * initialize the update listener after creation
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * processing incoming updates and after redirect user depending on his {@link pro.sky.telegrambot.enums.BotState}
     *
     * @param updates available updates
     * @return verification code for updates
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);
            Message message = update.message();
            Long chatId = message.chat().id();
            LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            if (update.message() != null && message.text() != null) {
                user.setName(message.chat().firstName());
                user.setSurname(message.chat().lastName());
                String text = message.text();
                List<User> userList = userRepository.findAll();
                if (userList.contains(userRepository.findUserByNameAndSurname(user.getName(), user.getSurname()))){
                        userRepository.save(user);
                    }
                switch (user.getBotState()) {
                    case START:
                        start.acceptStartCommands(user, text, chatId);
                        break;
                    case CHOOSE_SHELTER:
                        choseShelter.acceptChoseShelterComand(user, text, chatId);
                        break;
                    case MENU:
                        menu.acceptInfoCommands(user, text, chatId);
                        break;
                    case INFO:
                        info.acceptInfoCommands(user, text, chatId);
                        break;
                    case ADOPTION:
                        adoption.adoptionMenu(user, text, chatId);
                        break;
                    case REPORT:
                        PetReport petReport = new PetReport(user, dateTime, message.photo(), text, chatId);
                        petReportService.savePetReport(petReport);
                        break;
                    case HELP:
                        Help help = new Help(user, text);
                        helpService.saveHelpAppeal(help);
                        break;
                    default:
                        telegramBotService.sendMessage(
                                chatId,
                                user.getName() + ", пока не знаю ответа! Чтобы вернуться к началу, отправьте /start");
                        LOGGER.warn("Unrecognized message in " + chatId + " chat.");
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // return id of last processed update or confirm them all
    }
}

