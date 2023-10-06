package pro.sky.telegrambot.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.service.AppealToVolunteerService;

import java.util.Collection;

/**
 * controller of {@link AppealToVolunteerService}
 * <br>
 * url for test: "<a href="http://localhost:8082/swagger-ui/index.html">...</a>"
 */
@RestController
@RequestMapping("appeal")
public class AppealToVolunteerController {

    /**
     * service for getting methods
     */
    private final AppealToVolunteerService appealToVolunteerService;


    public AppealToVolunteerController(AppealToVolunteerService appealToVolunteerService) {
        this.appealToVolunteerService = appealToVolunteerService;
    }

    @Operation(
           requestBody = @RequestBody(
                   description = "save new appeal",
                   content = @Content(
                           mediaType = MediaType.APPLICATION_JSON_VALUE,
                           schema = @Schema(implementation = AppealToVolonteer.class)
                           )
           ),
            tags = "Appeal to volunteer"
    )
    @PostMapping
    public AppealToVolonteer saveAppeal(@Parameter(description = "saving appeal") AppealToVolonteer appealToVolonteer){
        return appealToVolunteerService.saveAppeal(appealToVolonteer);
    }

    @Operation(
            requestBody = @RequestBody(
                    description = "all appeals to volunteer",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AppealToVolonteer.class)
                            )
                    ),
            tags = "Appeal to volunteer"
            )
    @GetMapping
    public ResponseEntity<Collection<AppealToVolonteer>> getAllAppeals(){
        return ResponseEntity.ok(appealToVolunteerService.getAllAppeals());
    }
}
