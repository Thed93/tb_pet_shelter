package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.TelegramBotService;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class EndOfProbation {

    private final VolunteerService volunteerService;
    private final TelegramBotService telegramBotService;

    public EndOfProbation(VolunteerService volunteerService, TelegramBotService telegramBotService) {
        this.volunteerService = volunteerService;
        this.telegramBotService = telegramBotService;
    }

    public void commands(String text, Long volunteerId) {

        switch (text) {
            case "/approve":
                volunteerService.approveAdopt(volunteerId);
                break;
            case "/deny":
                volunteerService.denyAdopt(volunteerId);
                break;
            case "/extend":
                volunteerService.setState(volunteerService.getVolunteer(volunteerId), "EXTEND_PROBATION");
                telegramBotService.sendMessage(volunteerId, "Насколько дней продлить испытательный срок?");
                //volunteerService.extendProbation(volunteerId);
                break;
        }
    }
}
