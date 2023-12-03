package pro.sky.telegrambot.commands;

import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.VolunteerService;

@Component
public class VolunteerMenu {

    private final VolunteerService volunteerService;

    public VolunteerMenu(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    public void commands(String text, Long volunteerId) {

        switch (text) {
            case "/unverified_reports":
                volunteerService.setState(volunteerService.getVolunteer(volunteerId), "UNVERIFIED_REPORTS");
                volunteerService.sendUnverifiedReport(volunteerId);
                break;
            case "/no_reports":
                //volunteerService.setState(volunteerService.getVolunteer(volunteerId), "NO_REPORTS");
                volunteerService.sendUserContactsWithoutReports(volunteerId);
                break;
            case "/end_of_probation":
                volunteerService.setState(volunteerService.getVolunteer(volunteerId), "END_OF_PROBATION");
                volunteerService.endOfProbation(volunteerId);
                break;
        }
    }
}
