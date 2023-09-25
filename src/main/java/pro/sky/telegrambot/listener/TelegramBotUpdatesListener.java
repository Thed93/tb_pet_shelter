package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);


    private ShelterType shelterType;

    private User user;


    private final TelegramBotService telegramBotService;


    private final Handlers handlers;


    private final TelegramBot telegramBot;


    private final PetReportService petReportService;


    private final UserService userService;

    public TelegramBotUpdatesListener(TelegramBotService telegramBotService, Handlers handlers, TelegramBot telegramBot, PetReportService petReportService, UserService userService) {
        this.telegramBotService = telegramBotService;
        this.handlers = handlers;
        this.telegramBot = telegramBot;
        this.petReportService = petReportService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            Message message = update.message();
            Long chatId = message.chat().id();
            LocalDateTime dateTime = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
            if (update.message() != null && message.text() != null) {
                String userName = message.chat().firstName();
                String userSurname = message.chat().lastName();
                String text = message.text();



                //приветствие
                if (text.equals("/start")) {
                    user.setBotState(BotState.START);
                    user.setHasChosenShelter(false);
                    user.setCurrentChosenShelter(null);
                    user.setName(userName);
                    user.setSurname(userSurname);
                    userService.saveUser(user);
                    telegramBotService.sendMessage(chatId,
                            userName + " , приветствую вас!\n" +
                                    "Я - учебный бот, симулирующий работу приюта для животных. \n" +
                                    "Для дальнейшей работы напишите, какого типа приют вас интересует: \n" +
                                    "'/dog' - для собак, '/cat' - для кошек");

                    //выбор приюта
                } else if (text.equals("/dog") && user.getBotState() == BotState.START && user.isHasChosenShelter()) {
                    user.setBotState(BotState.CHOOSE_SHELTER);
                    user.setCurrentChosenShelter(ShelterType.DOG_SHELTER);
                    user.setHasChosenShelter(true);
                    handlers.handleShelterConsultation(chatId, text);
                } else if (text.equals("/cat") && user.getBotState() == BotState.START && user.isHasChosenShelter()) {
                    user.setBotState(BotState.CHOOSE_SHELTER);
                    user.setCurrentChosenShelter(ShelterType.CAT_SHELTER);;
                    user.setHasChosenShelter(true);
                    handlers.handleShelterConsultation(chatId, text);
                }
                //получение информации о приюте
                 else if (text.equals("/information") && user.getBotState() == BotState.CHOOSE_SHELTER) {
                    handlers.handleAdoptionConsultation(chatId, text);
                    }
                //уточнение более конкрейтной информации
                else if (text.equals("/about") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    handlers.aboutShelter(chatId, user.getCurrentChosenShelter());
                }
                //уточнение рабочих часов
                else if (text.equals("/workingHours") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    handlers.workingHours(chatId, user.getCurrentChosenShelter());
                }
                //узнать номер охраны
                else if (text.equals("/securityNumber") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    handlers.securityNumber(chatId, user.getCurrentChosenShelter());
                }
                //узнать рекомандации перед посещением приюта
                else if (text.equals("/safetyPrecautions") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    handlers.safetyPrecautions(chatId, user.getCurrentChosenShelter());
                }
                //записать свои данные
                else if (text.equals("/writeData") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    handlers.writeData(chatId);
                }
                //Этап 2. Консультация с потенциальным хозяином животного из приюта
                else if (text.equals("/howToTakePet") && user.getBotState() == BotState.CHOOSE_SHELTER){
                    user.setBotState(BotState.MENU);
                    handlers.howToTakePet(chatId, user.getCurrentChosenShelter());
                }
                //
                else if (text.equals("/welcomeRules") && user.getBotState() == BotState.MENU) {
                    handlers.welcomeRules(chatId, user.getCurrentChosenShelter());
                }
                else if (text.equals("/docs") && user.getBotState() == BotState.MENU) {
                    handlers.docs(chatId);
                }
                else if (text.equals("/petTransportation") && user.getBotState() == BotState.MENU) {
                    handlers.petTransportation(chatId);
                }
                else if (text.equals("/babyPetHouse") && user.getBotState() == BotState.MENU) {
                    handlers.babyPetHouse(chatId);
                }
                else if (text.equals("/petHouse") && user.getBotState() == BotState.MENU) {
                    handlers.petHouse(chatId);
                }
                else if (text.equals("/specialPetHouse") && user.getBotState() == BotState.MENU) {
                    handlers.specialPetHouse(chatId);
                }
                else if (text.equals("/adviceDogHandler") && user.getBotState() == BotState.MENU) {
                    handlers.adviceDogHandler(chatId);
                }
                else if (text.equals("/dogHandler") && user.getBotState() == BotState.MENU) {
                    handlers.dogHandler(chatId);
                }
                else if (text.equals("/refusePet") && user.getBotState() == BotState.MENU) {
                    handlers.refusePet(chatId);
                }
                else if (text.equals("/volunteer")) {
                    handlers.volunteer(chatId);
                }
                //Этап 3. Ведение питомца
                else if (text.equals("/report") && user.getBotState() == BotState.CHOOSE_SHELTER) {
                    user.setBotState(BotState.REPORT);
                    telegramBotService.sendMessage(chatId, "Для отчета о вашем животном отправьте пожалуйста фото и текст.");
                }
                else if (user.getBotState() == BotState.REPORT) {
                    petReportService.savePetReport(userName, userSurname, dateTime, message.photo(), message.text(), chatId  );
                }
                else {
                    telegramBotService.sendMessage(
                            chatId,
                            userName + ", пока не знаю ответа! Чтобы вернуться к началу, отправьте /start");
                    logger.warn("Unrecognized message in " + chatId + " chat.");
                }
            } else {
                telegramBotService.sendMessage(chatId,
                        "Отправьте команду /start или выберите тип приюта");
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL; // return id of last processed update or confirm them all
    }
}

