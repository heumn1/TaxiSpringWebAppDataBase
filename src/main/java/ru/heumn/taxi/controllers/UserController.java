package ru.heumn.taxi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.UserRepository;
import ru.heumn.taxi.service.UserService;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public String registration(@ModelAttribute("User") @Valid User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "registration";
        }

        System.out.println(user);

        if(!userService.addUser(user))
        {
            model.addAttribute("message", "User exists");
            return "registration";
        }
        return "redirect:/login";
    }
}