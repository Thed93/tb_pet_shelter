package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import liquibase.pro.packaged.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.commands.*;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
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

import static pro.sky.telegrambot.enums.BotState.START;

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
            if (update.message() != null && message.text() != null) {
//                List<UserChat> userList = userChatRepository.findAll();
                String text = message.text();
                UserChat defaultUser = new UserChat(chatId, userName, userSurname, null, false, Commands.START.getCommandText());
                UserChat user = new UserChat(chatId, userName, userSurname, null, false, Commands.START.getCommandText());
                if (text.equals(Commands.START.getCommandText())){
                    user.setBotState(defaultUser.getBotState());
                    user.setHasChosenShelter(defaultUser.isHasChosenShelter());
                    user.setCurrentChosenShelter(defaultUser.getCurrentChosenShelter());
                    BotState currentState = BotState.valueOf(user.getBotState());
                    userChatRepository.save(user);
                    handlers.startCommand(chatId,user);
                    switch (currentState) {
                        case START:
                            start.acceptStartCommands(user, chatId);
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
                            PetReport petReport = new PetReport(user, dateTime, message.photo(), text);
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

            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // return id of last processed update or confirm them all
    }
}