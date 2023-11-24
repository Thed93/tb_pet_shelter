package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.exception.UserNotFoundException;
import pro.sky.telegrambot.repository.UserChatRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * service for processing commands
 */
@Service
public class UserChatService {

    /**
     * class for adding message to programmer
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserChatService.class);

    /**
     * repository for data access
     */
    private final UserChatRepository userChatRepository;

    public UserChatService(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
    }

    public BotState getUserChatStatus(long id) {
        LOGGER.info(userChatRepository.findUserChatByUserId(id).get().getBotState());
        return BotState.valueOf(userChatRepository.findUserChatByUserId(id).orElseThrow(UserNotFoundException::new).getBotState());
    }

    public void setUserChatStatus(Long chatId, BotState botState) {
        UserChat userChat = userChatRepository.findUserChatByUserId(chatId).orElseThrow(UserNotFoundException::new);
        userChat.setBotState(botState.toString());
        userChatRepository.save(userChat);
    }

    /**
     *
     * get all useres, that use Telegram - bot
     * use repository method {@link JpaRepository#findAll()}
     *
     * @return all users from database
     */
    public Collection<UserChat> getAllUsers (){
        return userChatRepository.findAll();
    }

    @Transactional
    public void editUserChat(long chatId, String name, String userName) {
        UserChat userChat = new UserChat(chatId, name, userName, null, false, BotState.START.toString());
        if (userChatRepository.findById(chatId).isEmpty()) {
            userChatRepository.save(userChat);
        } else {
            userChat.equals(userChatRepository.findById(chatId));
        }
    }

    @Transactional
    public void setChoseShelter(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.CHOOSE_SHELTER.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setMenu(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.MENU.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setInfo(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.INFO.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setAdoption(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.ADOPTION.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setHelp(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.HELP.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setVolunteer(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.VOLUNTEER.toString());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setReport(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setBotState(BotState.REPORT.toString());
        userChatRepository.save(userChat);
    }

    public UserChat findById(Long id) {
        return userChatRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void setCat(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setCurrentChosenShelter(ShelterType.CAT_SHELTER.name());
        userChatRepository.save(userChat);
    }

    @Transactional
    public void setDog(long chatId){
        UserChat userChat = findById(chatId);
        userChat.setCurrentChosenShelter(ShelterType.DOG_SHELTER.name());
        userChatRepository.save(userChat);
    }

    @Transactional
    public String getName(long chatId){
        UserChat userChat = findById(chatId);
        return userChat.getName();
    }

    @Transactional
    public String getShelter(long chatId){
        UserChat userChat = findById(chatId);
        return userChat.getCurrentChosenShelter();
    }
}