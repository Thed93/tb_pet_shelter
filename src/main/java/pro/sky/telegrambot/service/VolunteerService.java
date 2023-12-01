package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.entity.Volunteer;
import pro.sky.telegrambot.repository.VolunteerRepository;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final UserChatService userChatService;

    public VolunteerService(VolunteerRepository volunteerRepository, UserChatService userChatService) {
        this.volunteerRepository = volunteerRepository;
        this.userChatService = userChatService;
    }

    public Volunteer getVolunteer(Long volunteerId) {
        return volunteerRepository.findById(volunteerId).orElse(null);
    }

    public void callVolunteer(Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        Volunteer volunteer = volunteerRepository.findByUser(null);
        volunteer.setUser(userChat);
        volunteer.setState("CONVERSATION_WITH_USER");
        volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteerByUser(UserChat userChat) {
        return volunteerRepository.findByUser(userChat);
    }

}
