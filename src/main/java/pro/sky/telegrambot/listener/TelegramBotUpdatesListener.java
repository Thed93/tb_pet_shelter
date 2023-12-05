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
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.service.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    /**
     * class for adding message to programmer
     */
    private final Logger LOGGER = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final Start start;
    private final Menu menu;
    private final VolunteerMenu volunteerMenu;
    private final UnverifiedReports unverifiedReports;
    private final EndOfProbation endOfProbation;
    private final ExtendProbation extendProbation;
    private final Adoption adoption;
    private final Info info;
    private final ChoseShelter choseShelter;
    private final PetReportService petReportService;
    private final Back back;
    private final UserChatService userChatService;
    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;
    private final ConversationService conversationService;

    public TelegramBotUpdatesListener(Start start,
                                      Menu menu,
                                      VolunteerMenu volunteerMenu,
                                      UnverifiedReports unverifiedReports,
                                      EndOfProbation endOfProbation,
                                      ExtendProbation extendProbation,
                                      Adoption adoption,
                                      Info info,
                                      ChoseShelter choseShelter,
                                      PetReportService petReportService,
                                      Back back,
                                      UserChatService userChatService,
                                      VolunteerService volunteerService,
                                      TelegramBotService telegramBotService,
                                      ConversationService conversationService,
                                      TelegramBot telegramBot) {
        this.start = start;
        this.menu = menu;
        this.volunteerMenu = volunteerMenu;
        this.unverifiedReports = unverifiedReports;
        this.endOfProbation = endOfProbation;
        this.extendProbation = extendProbation;
        this.adoption = adoption;
        this.info = info;
        this.choseShelter = choseShelter;
        this.petReportService = petReportService;
        this.back = back;
        this.userChatService = userChatService;
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
        this.conversationService = conversationService;
        this.telegramBot = telegramBot;
    }

    @Autowired
    private final TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            LOGGER.info("Processing update: {}", update);
            Message message = update.message();
            Long id = message.chat().id();

            if (update.message() != null && (message.text() != null || message.photo() != null)) {
                Volunteer volunteer = volunteerService.getVolunteer(id);

                if (volunteer != null) {
                    volunteerPart(id, message, volunteer);
                } else {
                    userPart(id, message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // return id of last processed update or confirm them all
    }

    public void userPart(Long chatId, Message message) {
        String text = message.text();
        String userName = message.chat().firstName();
        String userSurname = message.chat().lastName();

        userChatService.editUserChat(chatId, userName, userSurname);
        if (Commands.START.getCommandText().equals(text)) {
            userChatService.setStartState(chatId);
        }
        if (Commands.BACK.getCommandText().equals(text)) {
            back.goBack(chatId, text);
        }
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
            case CHOOSE_PET:
                petReportService.createReport(text, chatId);
            case REPORT:
                petReportService.complementReport(text, message.photo(), chatId);
                break;
            case CONVERSATION_WITH_VOLUNTEER:
                conversationService.sendMessageToVolunteer(text, chatId);
                break;
            case HELP:
                break;
            default:
                telegramBotService.sendMessage(
                        chatId,
                        userChatService.getName(chatId) + ", пока не знаю ответа! Чтобы вернуться к началу, отправьте /start");
                LOGGER.warn("Unrecognized message in " + chatId + " chat.");
        }
    }

    public void volunteerPart(Long volunteerId, Message message, Volunteer volunteer) {
        String text = message.text();
        String state = volunteer.getState();

        if ("/start".equals(message.text())) {
            volunteerService.setState(volunteer, "MENU");
            telegramBotService.sendMessage(volunteerId, "/unverified_reports - проверить новые отчеты\n" +
                    "/no_reports - усыновители, которые два и более дней не сдают отчеты\n" +
                    "/end_of_probation - решить остается ли животное у усыновителя\n");
        }

        switch (state) {
            case "MENU":
                volunteerMenu.commands(text, volunteerId);
                break;
            case "UNVERIFIED_REPORTS":
                unverifiedReports.commands(text, volunteerId);
                break;
            case "END_OF_PROBATION":
                endOfProbation.commands(text, volunteerId);
                break;
            case "EXTEND_PROBATION":
                extendProbation.commands(text, volunteerId);
                break;
            case "CONVERSATION_WITH_USER":
                conversationService.sendMessageToUser(message.text(), volunteerId);
                break;
        }
    }
}