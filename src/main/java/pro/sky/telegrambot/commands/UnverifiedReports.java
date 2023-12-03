package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class UnverifiedReports {

    private final VolunteerService volunteerService;

    public UnverifiedReports(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    public void commands(String text, Long volunteerId) {

        switch (text) {
            case "/approve":
                volunteerService.approveReport(volunteerId);
                break;
            case "/deny":
                volunteerService.denyReport(volunteerId);
        }
    }
}
