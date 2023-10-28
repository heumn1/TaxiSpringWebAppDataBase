package ru.heumn.taxi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.heumn.taxi.domain.Role;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.UserRepository;
import ru.heumn.taxi.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    DriverRepository driverRepository;


    @GetMapping("/account")
    public String account(){

        return "account";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("User") @Valid User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "registration";
        }


        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.USER);
        user.setRoles(roleSet);
        user.setActive(true);

        if(!userService.addUser(user))
        {
            model.addAttribute("message", "User exists");
            return "registration";
        }
        return "redirect:/";
    }
}
