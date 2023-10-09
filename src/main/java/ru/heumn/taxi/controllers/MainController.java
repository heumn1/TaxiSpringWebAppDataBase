package ru.heumn.taxi.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import ru.heumn.taxi.ChatMessage;
import ru.heumn.taxi.domain.User;

import java.security.Principal;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(){
        return "index";
    }



    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage( Principal principal
    ) {

        ChatMessage chatMessage = new ChatMessage();

        System.out.println(principal.getName());

        return chatMessage;
    }



    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model){

        try{
            if(error.isEmpty())
            {
                error = "Неправильный логин или пароль";
            }
        }
        catch (Exception ignored){}

        model.addAttribute("userError", error);
        return "login";
    }
    @GetMapping("/test")
    public String gdsgds(){
        return "test";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("User") User user ){
        return "registration";
    }
}
