package pro.sky.telegrambot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.service.PetReportService;

import java.util.Collection;

/**
 * controller of {@link PetReportService}
 * <br>
 * url for test: "<a href="http://localhost:8082/swagger-ui/index.html">...</a>"
 */
@RestController
@RequestMapping("pet_report")
public class PetReportController {

    /**
     * service for getting methods
     */
    private final PetReportService petReportService;

    public PetReportController(PetReportService petReportService) {
        this.petReportService = petReportService;
    }

    @Operation(
            requestBody = @RequestBody(
                    description = "save pet report",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PetReport.class)
                    )
            ),
            tags = "Pet report"
    )
    @PostMapping
    public void saveReport(@Parameter(description = "saving report") PetReport petReport){
        petReportService.savePetReport(petReport);
    }

    @Operation
            (requestBody = @RequestBody(
                    description = "all reports",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = PetReport.class))
                    )),
                    tags = "Pet report"
            )
    @GetMapping
    public ResponseEntity<Collection<PetReport>> getAllReports(){
        return ResponseEntity.ok(petReportService.getAllReports());
    }

    @Operation
            (requestBody = @RequestBody(
                                    description = "all user reports",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = PetReport.class))
                                    )),
                    tags = "Pet report"
            )
    @GetMapping("by_name_and_surname")
    public ResponseEntity<Collection<PetReport>> getReportsByNameAndSurname(@Parameter(description = "user name", example = "Ivan")@RequestParam String name, @Parameter(description = "user surname", example = "Ivanov")@RequestParam String surname){
        return ResponseEntity.ok(petReportService.getReportsByUserNameAndSurname(name, surname));
    }


}
