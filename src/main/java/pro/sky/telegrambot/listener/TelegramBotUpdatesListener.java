package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.handle.Handlers;
import pro.sky.telegrambot.service.TelegramBotService;
import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private BotState currentState;

    private boolean hasChosenShelter;
    private String chosenShelter;

    @Autowired
    private TelegramBotService telegramBotService;

    @Autowired
    private Handlers handlers;

    @Autowired
    private TelegramBot telegramBot;

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
            LocalDateTime dateTime;
            if (update.message() != null && message.text() != null) {
                String userName = message.chat().firstName();
                String text = message.text();

                //приветствие
                if (text.equals("/start")) {
                    currentState = BotState.START;
                    hasChosenShelter = false;
                    chosenShelter = null;
                    telegramBotService.sendMessage(chatId,
                            userName + " , приветствую вас!\n" +
                                    "Я - учебный бот, симулирующий работу приюта для животных. \n" +
                                    "Для дальнейшей работы напишите, какого типа приют вас интересует: \n" +
                                    "'/dog' - для собак, '/cat' - для кошек");

                    //выбор приюта
                } else if (text.equals("/dog") && currentState == BotState.START && !hasChosenShelter) {
                    currentState = BotState.CHOOSE_SHELTER;
                    chosenShelter = "Приют для собак";
                    hasChosenShelter = true;
                    handlers.handleShelterConsultation(chatId, text);
                } else if (text.equals("/cat") && currentState == BotState.START && !hasChosenShelter) {
                    currentState = BotState.CHOOSE_SHELTER;
                    chosenShelter = "Приют для кошек";
                    hasChosenShelter = true;
                    handlers.handleShelterConsultation(chatId, text);
                }
                //получение информации о приюте
                 else if (text.equals("/information") && currentState == BotState.CHOOSE_SHELTER) {
                    handlers.handleAdoptionConsultation(chatId, text);
                    }
                //уточнение более конкрейтной информации
                else if (text.equals("/about") && currentState == BotState.CHOOSE_SHELTER){
                    handlers.aboutShelter(chatId, chosenShelter);
                }
                //уточнение рабочих часов
                else if (text.equals("/workingHours") && currentState == BotState.CHOOSE_SHELTER){
                    handlers.workingHours(chatId, chosenShelter);
                }
                //узнать номер охраны
                else if (text.equals("/securityNumber") && currentState == BotState.CHOOSE_SHELTER){
                    handlers.securityNumber(chatId, chosenShelter);
                }
                //узнать рекомандации перед посещением приюта
                else if (text.equals("/safetyPrecautions") && currentState == BotState.CHOOSE_SHELTER){
                    handlers.safetyPrecautions(chatId, chosenShelter);
                }
                //записать свои данные
                else if (text.equals("/writeData") && currentState == BotState.CHOOSE_SHELTER){
                    handlers.writeData(chatId);
                }
                //Этап 2. Консультация с потенциальным хозяином животного из приюта
                else if (text.equals("/howToTakePet") && currentState == BotState.CHOOSE_SHELTER){
                    currentState = BotState.MENU;
                    handlers.howToTakePet(chatId, chosenShelter);
                }
                //
                else if (text.equals("/welcomeRules") && currentState == BotState.MENU) {
                    handlers.welcomeRules(chatId, chosenShelter);
                }
                else if (text.equals("/docs") && currentState == BotState.MENU) {
                    handlers.docs(chatId);
                }
                else if (text.equals("/petTransportation") && currentState == BotState.MENU) {
                    handlers.petTransportation(chatId);
                }
                else if (text.equals("/babyPetHouse") && currentState == BotState.MENU) {
                    handlers.babyPetHouse(chatId);
                }
                else if (text.equals("/petHouse") && currentState == BotState.MENU) {
                    handlers.petHouse(chatId);
                }
                else if (text.equals("/specialPetHouse") && currentState == BotState.MENU) {
                    handlers.specialPetHouse(chatId);
                }
                else if (text.equals("/adviceDogHandler") && currentState == BotState.MENU) {
                    handlers.adviceDogHandler(chatId);
                }
                else if (text.equals("/dogHandler") && currentState == BotState.MENU) {
                    handlers.dogHandler(chatId);
                }
                else if (text.equals("/refusePet") && currentState == BotState.MENU) {
                    handlers.refusePet(chatId);
                }
                else if (text.equals("/volunteer")) {
                    handlers.volunteer(chatId);
                }
                //Этап 3. Ведение питомца
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

