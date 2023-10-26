package pro.sky.telegrambot.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.UserChat;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.repository.HelpRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpTest {

    @Mock
    private HelpRepository helpRepository;

    @InjectMocks
    private HelpService helpService;

    final UserChat user = new UserChat("Леха", "Мятый", ShelterType.DOG_SHELTER, true, BotState.START);

    final Help help = new Help(user, "Позвоните мне завтра");

    List<Help> helps = new ArrayList<>();

    @Test
    void save(){
        when(helpRepository.save(any(Help.class))).thenReturn(help);
        helpService.saveHelpAppeal(help);
        verify(helpRepository, Mockito.times(1)).save(help);
    }

    @Test
    void getAll(){
        when(helpRepository.findAll()).thenReturn(helps);
        helpService.getAllHelps();
        verify(helpRepository, Mockito.times(1)).findAll();
    }
}
