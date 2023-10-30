package pro.sky.telegrambot.controller;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.telegrambot.entity.PetReport;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.TelegramBotService;

/**
 * controller of {@link TelegramBotService}
 * <br>
 * url for test: "<a href="http://localhost:8082/swagger-ui/index.html">...</a>"
 */

@RestController
@RequestMapping("tg_bot")
public class TelegramBotController {

    /**
     * service for getting methods
     */
    private final TelegramBotService telegramBotService;

    public TelegramBotController(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @Operation
            (summary = "send message for user",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "message send",
                                    content = @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema(schema = @Schema(implementation = TelegramBot.class))
                                    )
                            )
                    },
                    tags = "Telegram bot"
            )
    @GetMapping
    public void sendMessage (@Parameter(description = "user's id", example = "12345") long chatId, @Parameter(description = "message", example = "u r frog") String text, @Nullable ParseMode parseMode){
        ResponseEntity.ok();
    }
}
