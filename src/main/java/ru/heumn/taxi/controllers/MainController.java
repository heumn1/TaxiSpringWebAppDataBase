package ru.heumn.taxi.controllers;

import jakarta.persistence.Table;
import jakarta.servlet.http.HttpSession;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.heumn.taxi.ChatMessage;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.TripRepository;
import ru.heumn.taxi.repos.UserRepository;

import java.security.Principal;
import java.util.List;

@Controller
@ToString

public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(Principal principal
    ) {

        ChatMessage chatMessage = new ChatMessage();

        System.out.println(principal.getName());

        return chatMessage;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {

        try {
            if (error.isEmpty()) {
                error = "Неправильный логин или пароль";
            }
        } catch (Exception ignored) {
        }

        model.addAttribute("userError", error);
        return "login";
    }
    @GetMapping("/test")
    public String gdsgds(HttpSession session) {
        String sessionId = session.getId();
        System.out.println(sessionId);
        List<Trip> trips = tripRepository.findByDriver_IdIsNull();

        trips.stream()
                .forEach((k) -> {System.out.println(k + "\n ________________");});

        return "test";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("User") User user) {
        return "registration";
    }

    @GetMapping("/accessDenied")
    public String accessDenied(){
        return "accessDenied";
    }
}
