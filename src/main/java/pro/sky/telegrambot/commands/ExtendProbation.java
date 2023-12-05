package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class ExtendProbation {

    private final VolunteerService volunteerService;

    public ExtendProbation(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    public void commands(String text, Long volunteerId) {
        volunteerService.extendProbation(volunteerId, text);
    }
}
