package pro.sky.telegrambot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.telegrambot.entity.User;
import pro.sky.telegrambot.enums.BotState;
import pro.sky.telegrambot.enums.ShelterType;
import pro.sky.telegrambot.service.PetReportService;
import pro.sky.telegrambot.service.UserService;

import java.util.Collection;

/**
 * controller of {@link UserService}
 * <br>
 * url for test: "<a href="http://localhost:8082/swagger-ui/index.html">...</a>"
 */
@RestController
@RequestMapping("user")

public class UserController {

    /**
     * service for getting methods
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User saveUser (@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping
    ResponseEntity<Collection<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
