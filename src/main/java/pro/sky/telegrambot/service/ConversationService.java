package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.commands.ChoseShelter;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.enums.ShelterType;

@Service
public class ConversationService {

    private final UserChatService userChatService;
    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;
    private final ChoseShelter choseShelter;

    public ConversationService(UserChatService userChatService,
                               VolunteerService volunteerService,
                               TelegramBotService telegramBotService,
                               ChoseShelter choseShelter) {
        this.userChatService = userChatService;
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
        this.choseShelter = choseShelter;
    }


    public void sendMessageToVolunteer(String text, Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        Volunteer volunteer = volunteerService.getVolunteerByUser(userChat);

        telegramBotService.sendMessage(volunteer.getId(), text);
    }

    public void sendMessageToUser(String text, Long volunteerId) {
        Volunteer volunteer = volunteerService.getVolunteer(volunteerId);

        if (text.equals("/stop")) {
            stopConversation(volunteerId, volunteer.getUser().getUserId());
        } else {
            telegramBotService.sendMessage(volunteer.getUser().getUserId(), text);
        }
    }

    private void stopConversation(Long volunteerId, Long chatId) {
        volunteerService.stopConversation(volunteerId);
        userChatService.stopConversation(chatId);
    }
}
