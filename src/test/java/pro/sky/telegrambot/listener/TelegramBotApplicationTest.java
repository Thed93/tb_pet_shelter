package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.AppealToVolunteer;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.UserChatService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TelegramBotApplicationTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private com.pengrad.telegrambot.model.User user;

    @Mock
    private UserChatService userChatService;

    @Mock
    private Help help;

    @Mock
    private PetReport petReport;

    @Mock
    private TelegramBotService telegramBotService;

    @Mock
    private AppealToVolunteer appeal;

    @Mock
    private Update update;

    @Mock
    private UserChat userChat;

    @Mock
    private Chat chat;

    @Mock
    private Message message;

    @InjectMocks
    private TelegramBotUpdatesListener out;

    private static final String TEXT = "/";
    private static final Long CHAT_ID = 1L;
    private static final String NAME = "name";

    private static final String USERNAME = "username";

    final UserChat testUser = new UserChat("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    @Test
    void initTest() {
        out.init();
        verify(telegramBot).setUpdatesListener(any());
    }



    @Test
    void processTest() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(TEXT);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
        when(chat.id()).thenReturn(CHAT_ID);
        when(user.firstName()).thenReturn(NAME);
        when(user.username()).thenReturn(USERNAME);

        when(userChat.getBotState()).thenReturn(BotState.START);
        out.process(List.of(update));
        verify(telegramBotService).sendMessage(CHAT_ID, TEXT);
    }

}
