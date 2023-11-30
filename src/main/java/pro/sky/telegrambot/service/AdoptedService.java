package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Adopted;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.repository.AdoptedRepository;

/**
 * service for processing adoption requests
 */
@Service
public class AdoptedService {

    private final AdoptedRepository adoptedRepository;

    public AdoptedService(AdoptedRepository adoptedRepository) {
        this.adoptedRepository = adoptedRepository;
    }

    /**
     * create note about adoption and adds it to the database
     * @param userChat
     * @param pet
     */
    public void createAdopted(UserChat userChat, Pet pet) {
        Adopted adopted = new Adopted();
        adopted.setUserChat(userChat);
        adopted.setPet(pet);
        adoptedRepository.save(adopted);
    }
}
