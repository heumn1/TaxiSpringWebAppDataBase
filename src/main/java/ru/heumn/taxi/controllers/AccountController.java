package ru.heumn.taxi.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.Role;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.CarRepository;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.TripRepository;
import ru.heumn.taxi.repos.UserRepository;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static List<User> usersDriver;

    private static List<Driver> driverList;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TripRepository tripRepository;

    @PostMapping("/history")
    public String getHistoryOrders(Model model, Principal principal){

        List<Trip> tripList;

        tripList = tripRepository.findAllByUser(userRepository.findByUsername(principal.getName()));

        if(tripList.isEmpty())
        {
            model.addAttribute("NoHistory", "У вас пока не было заказов");
        }
        else
        {
            model.addAttribute("History", tripList);
        }

        return "account";
    }

    @PostMapping("/info")
    public String getAccountInfo(Model model, Principal principal){

        User user = userRepository.findByUsername(principal.getName());

        if(driverRepository.findByIdUser(user) != null)
        {
            model.addAttribute("DriverInfo", driverRepository.findByIdUser(user));
            model.addAttribute("CarInfo", carRepository.findByDriver_Id(driverRepository.findByIdUser(user).getId()).toString());
        }

        model.addAttribute("UserInfo", user);

        return "account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/list")
    public String getListUsers(Model model){

        List<User> users =  userRepository.findAll();
        model.addAttribute("users", users);

        return "account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/listSet")
    public String getLisSettUsers(Model model, @ModelAttribute("Driver") @Valid Driver driver){

        List<User> users =  userRepository.findAll();

        users = users.stream()
                .filter(user -> !user.getRoles().contains(Role.DRIVER))
                .collect(Collectors.toList());

        model.addAttribute("SetUsersToDriver", "Set");
        model.addAttribute("users", users);

        return "account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/listSet/{id}")
    public String getLisSettUsers(Model model, @PathVariable Long id){

        Optional<User> user =  userRepository.findById(id);

        if(user.isPresent() && !user.get().getRoles().contains(Role.DRIVER))
        {
            Set<Role> roleSet = new HashSet<Role>();
            roleSet.add(Role.DRIVER);

            user.get().setRoles(roleSet);

        }

        Optional<User> userToDriver = userRepository.findById(id);
        model.addAttribute("userToDriver", userToDriver.get());

        return "account";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/drivers")
    public String getListDrivers(Model model){

        Iterable<Driver> drivers =  driverRepository.findAll();
        model.addAttribute("CarRepository", carRepository);
        model.addAttribute("drivers", drivers);

        return "account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/drivers/delete")
    public String getDeleteDriver(Model model){

        Iterable<Driver> drivers =  driverRepository.findAll();

        usersDriver = new ArrayList<>();

        StreamSupport.stream(drivers.spliterator(), false)
                        .forEach(driver -> usersDriver.add(userRepository.findById(driver.getId()).get()));

        driverList = new ArrayList<>();

        usersDriver.forEach(this::setDrivers);


        model.addAttribute("CarRepository", carRepository);
        model.addAttribute("deleteDriver", "Delete");
        model.addAttribute("drivers", driverList);

        return "account";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/drivers/delete/{id}")
    public String getDeleteDriver(@PathVariable("id") Long id,  Model model){

        Optional<Driver> driver = driverRepository.findById(id);

        if (driver.isPresent())
        {
            Optional<User> user = userRepository.findById(driver.get().getIdUser().getId());

            if(user.isPresent())
            {
                Set<Role> rolesUser = user.get().getRoles();

                rolesUser = rolesUser.stream()
                        .filter(role -> role != Role.DRIVER)
                        .collect(Collectors.toSet());

                System.out.println(user);

                user.get().setRoles(rolesUser);
                userRepository.save(user.get());
            }
        }


        Iterable<Driver> drivers =  driverRepository.findAll();
        model.addAttribute("CarRepository", carRepository);
        model.addAttribute("drivers", drivers);

        return "account";
    }

    private void setDrivers(User user)
    {

        if(user.getRoles().contains(Role.DRIVER))
        {
            driverList.add(driverRepository.findByIdUser(user));
        }
    }

}

