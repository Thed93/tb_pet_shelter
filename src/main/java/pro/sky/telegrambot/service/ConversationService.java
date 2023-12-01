package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;

@Service
public class ConversationService {

    private final UserChatService userChatService;
    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;

    public ConversationService(UserChatService userChatService,
                               VolunteerService volunteerService,
                               TelegramBotService telegramBotService) {
        this.userChatService = userChatService;
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
    }


    public void sendMessageToVolunteer(String text, Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        Volunteer volunteer = volunteerService.getVolunteerByUser(userChat);

        telegramBotService.sendMessage(volunteer.getId(), text);
    }

    public void conversationWithUser(String text, Long id) {
        Volunteer volunteer = volunteerService.getVolunteer(id);

        if (text.equals("/stop")) {
            volunteerService.stopConversaton(id);
            userChatService.stopConversation(volunteer.getUser().getUserId());
        }

        telegramBotService.sendMessage(volunteer.getUser().getUserId(), text);
    }
}
