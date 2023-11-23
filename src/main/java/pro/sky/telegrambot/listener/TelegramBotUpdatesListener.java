package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.commands.*;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.repository.UserChatRepository;
import pro.sky.telegrambot.service.HelpService;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    /**
     * class for adding message to programmer
     */
    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final Start start;

    private final Menu menu;

    private final Adoption adoption;

    private final Info info;

    private final ChoseShelter choseShelter;

    private final UserChatRepository userChatRepository;

    private final PetReportService petReportService;

    private final HelpService helpService;

    private final UserChatService userChatService;

    private final TelegramBotService telegramBotService;

    private final Handlers handlers;

    public TelegramBotUpdatesListener(Start start, Menu menu, Adoption adoption, Info info, ChoseShelter choseShelter, UserChatRepository userChatRepository, PetReportService petReportService, HelpService helpService, UserChatService userChatService, TelegramBotService telegramBotService, Handlers handlers, TelegramBot telegramBot) {
        this.start = start;
        this.menu = menu;
        this.adoption = adoption;
        this.info = info;
        this.choseShelter = choseShelter;
        this.userChatRepository = userChatRepository;
        this.petReportService = petReportService;
        this.helpService = helpService;
        this.userChatService = userChatService;
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
        this.telegramBot = telegramBot;
    }

    @Autowired
    private TelegramBot telegramBot;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);
            Message message = update.message();
            String userName = message.chat().firstName();
            String userSurname = message.chat().lastName();
            Long chatId = message.chat().id();
            LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            if (update.message() != null && (message.text() != null || message.photo() != null)) {
                String text = message.text();
                userChatService.editUserChat(chatId, userName, userSurname);
                BotState currentState = userChatService.getUserChatStatus(chatId);
                LOGGER.info(currentState.toString());
                switch (currentState) {
                    case START:
                        start.acceptStartCommands(chatId);
                        break;
                    case CHOOSE_SHELTER:
                        choseShelter.acceptChoseShelterCommand(text, chatId);
                        break;
                    case MENU:
                        menu.acceptInfoCommands(text, chatId);
                        break;
                    case INFO:
                        info.acceptInfoCommands(text, chatId);
                        break;
                    case ADOPTION:
                        adoption.adoptionMenu(text, chatId);
                        break;
                    case REPORT:
                        petReportService.newReport(chatId);
                        break;
                    case REPORT_PHOTO:
                        petReportService.reportPhoto(message.photo(), chatId);
                        break;
                    case REPORT_TEXT:
                        petReportService.reportText(text, chatId);
                        break;
                    case HELP:
                        break;
                    default:
                        telegramBotService.sendMessage(
                                chatId,
                                userChatService.getName(chatId) + ", пока не знаю ответа! Чтобы вернуться к началу, отправьте /start");
                        LOGGER.warn("Unrecognized message in " + chatId + " chat.");
                }
                //}

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // return id of last processed update or confirm them all
    }
}