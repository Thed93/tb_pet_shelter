package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Probation;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.repository.ProbationRepository;

import java.util.List;

@Service
public class ProbationService {

    private final ProbationRepository probationRepository;
    private final UserChatService userChatService;

    public ProbationService(ProbationRepository probationRepository, UserChatService userChatService) {
        this.probationRepository = probationRepository;
        this.userChatService = userChatService;
    }

    public List<Probation> getProbationByUserId(Long chatId) {
        UserChat userChat = userChatService.findById(chatId);
        return probationRepository.findAllByUserChat(userChat);
    }

}
