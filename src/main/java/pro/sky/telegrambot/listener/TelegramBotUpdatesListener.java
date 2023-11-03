package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.Commands;
import pro.sky.telegrambot.repository.UserChatRepository;
import pro.sky.telegrambot.service.TelegramBotService;
import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private boolean hasChosenShelter;
    private String chosenShelter;

    private final UserChatRepository userChatRepository;

    @Autowired
    private TelegramBotService telegramBotService;


    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
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
            if (update.message() != null && message.text() != null) {
                String userName = message.chat().firstName();
                String userSurname = message.chat().lastName();
                String text = message.text();
                List<UserChat> users = userChatRepository.findAll();

                //приветствие
                if (text.equals(Commands.START.getCommandText())) {
                    telegramBotService.sendMessage(chatId,
                            userName + " , приветствую вас!\n" +
                                    "Я - учебный бот, симулирующий работу приюта для животных. \n" +
                                    "Для дальнейшей работы напишите, какого типа приют вас интересует: \n" +
                                    "'/dog' - для собак, '/cat' - для кошек");
                    UserChat user = new UserChat(chatId, userName, userSurname);
                    if(!users.contains(user)){
                        user.setBotState(BotState.START.toString());
                        user.setCurrentChosenShelter(null);
                        user.setHasChosenShelter(false);
                        userChatRepository.save(user);
                    }
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

