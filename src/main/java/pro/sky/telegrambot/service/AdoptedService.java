package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.Adopted;
import pro.sky.telegrambot.entity.Pet;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.repository.AdoptedRepository;

@Service
public class AdoptedService {

    private final AdoptedRepository adoptedRepository;

    public AdoptedService(AdoptedRepository adoptedRepository) {
        this.adoptedRepository = adoptedRepository;
    }

    public Adopted createAdopted(UserChat userChat, Pet pet) {
        Adopted adopted = new Adopted();
        adopted.setUserChat(userChat);
        adopted.setPet(pet);
        return adoptedRepository.save(adopted);
    }
}
