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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.entity.AppealToVolonteer;
import pro.sky.telegrambot.entity.Help;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.service.AppealToVolunteerService;
import pro.sky.telegrambot.service.HelpService;

import java.util.Collection;

/**
 * controller of {@link HelpService}
 * <br>
 * url for test: "<a href="http://localhost:8082/swagger-ui/index.html">...</a>"
 */
@RestController
@RequestMapping("help")
public class HelpController {

    /**
     * service for getting methods
     */
    private final HelpService helpService;

    public HelpController(HelpService helpService) {
        this.helpService = helpService;
    }

    @Operation(
            requestBody = @RequestBody(
                    description = "save new appeal",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Help.class)
                    )
            ),
            tags = "Help"
    )
    @PostMapping
    public Help saveHelp(@Parameter(description = "saving appeal") Help help){
        return helpService.saveHelpAppeal(help);
    }

    @Operation
            (requestBody = @RequestBody(
                    description = "all appeals",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Help.class))
                                    )
                            ),
                    tags = "Help"
            )
    @GetMapping
    public ResponseEntity<Collection<Help>> getAllHelps(){
        return ResponseEntity.ok(helpService.getAllHelps());
    }
}
