package ru.heumn.taxi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.TripRepository;
import ru.heumn.taxi.repos.UserRepository;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/driver")
@PreAuthorize("hasAuthority('DRIVER')")
public class DriverController {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DriverRepository driverRepository;

    @GetMapping("/activeorder")
    public String activeOrder(Principal principal, Model model){

        Trip trip = tripRepository.findByDriver_IdAndActiveIsTrue(
                                            driverRepository.findByIdUser(
                                                userRepository.findByUsername(principal.getName())).getId());

        if(!(trip == null))
        {
            model.addAttribute("userDriver", trip.getDriver().getIdUser().getUsername());
            model.addAttribute("trip", trip);
        }
        else
        {
            model.addAttribute("noOrder", "Нет активного заказа");
        }

        return "driverOrder";
    }


    @PostMapping("/activeorder/{id}")
    public String endOrder(@PathVariable Long id, Principal principal){
        Optional<Trip> tempTrip = tripRepository.findById(id);
        Trip trip = tempTrip.get();

        if(!Objects.equals(driverRepository.findByIdUser(userRepository.findByUsername(principal.getName())).getId(), trip.getDriver().getId()))
        {
            return "redirect:/index";
        }

        Driver driver = driverRepository.findByIdUser(userRepository.findByUsername(principal.getName()));

        driver.setActiveOrder(false);
        trip.setActive(false);
        trip.setStatus("Закончен");

        driverRepository.save(driver);
        tripRepository.save(trip);

        return "redirect:/account/history";
    }
}
