package ru.heumn.taxi.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.DriverRepos;
import ru.heumn.taxi.repos.TripRepos;
import ru.heumn.taxi.repos.UserRepo;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    TripRepos tripRepos;
    @Autowired
    UserRepo userRepo;
    @Autowired
    DriverRepos driverRepos;

    @GetMapping()
    private String main(Model model){

        Trip trip = new Trip();
        List<String> cars = new ArrayList<>();
        cars.add("Эконом");
        cars.add("Люкс");
        cars.add("Грузовой");

        model.addAttribute("cars", cars);

        model.addAttribute("Trip", trip);
        return "order";
    }

    @PostMapping()
    public String order(@Valid Trip trip, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors())
        {
            return "order";
        }
        System.out.println(principal.getName());

        long unt = 1;
        Optional<Driver> driver = driverRepos.findById(unt);
        User user = userRepo.findByUsername("heumn");

        trip.setDriver(driver.get());
        trip.setUser(user);
        trip.setPrice("120");


        System.out.println(trip);

        tripRepos.save(trip);

        return "account";
    }
}
