package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.ProbationService;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class EndOfProbation {

    private final VolunteerService volunteerService;

    public EndOfProbation(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    public void commands(String text, Long volunteerId) {

        switch (text) {
            case "/approve":
                volunteerService.approveAdopt(volunteerId);
                break;
            case "/deny":
                volunteerService.denyAdopt(volunteerId);
                break;
        }
    }
}
