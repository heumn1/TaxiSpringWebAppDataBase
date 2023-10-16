package ru.heumn.taxi.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.CarRepository;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.TripRepository;
import ru.heumn.taxi.repos.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    TripRepository tripRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CarRepository carRepository;

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
    public String order(@Valid Trip trip, BindingResult bindingResult, Principal principal, HttpSession session) {

        if (bindingResult.hasErrors())
        {
            return "order";
        }
        User user = userRepository.findByUsername(principal.getName());

        trip.setDriver(null);
        trip.setUser(user);
        trip.setPrice("120");
        //TODO: АДМИН ЦЕНЫ ДОЛЖЕН НАСТРАИВАТЬ В СВОЕМ

        System.out.println(trip);

        tripRepository.save(trip);

        return "waiting";
    }

    @GetMapping("/orderlist")
    public String orderlist(Model model, @ModelAttribute("Trip") Trip trip, Principal principal){

        if(driverRepository.findByIdUser(userRepository.findByUsername(principal.getName())).isActiveOrder())
        {
            return "redirect:driverOrder";
        }

        List<Trip> trips = tripRepository.findByDriver_IdIsNullAndType(
                carRepository.findByDriver_Id(
                        driverRepository.findByIdUser(
                                userRepository.findByUsername(principal.getName()))
                                .getId()).getType());


        if(trips.isEmpty()){
            model.addAttribute("noOrders", "Заказов нет");
        }
        else{
            model.addAttribute("trips", trips);
        }
        return "orderlist";
    }

    @PostMapping("/orderlist/{id}")
    public String orderSubmit(@PathVariable Long id, Principal principal){
        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isPresent())
        {
            trip.get().setDriver(driverRepository.findByIdUser(userRepository.findByUsername(principal.getName())));
            tripRepository.save(trip.get());

            Driver driver = driverRepository.findByIdUser(userRepository.findByUsername(principal.getName()));
            driver.setActiveOrder(true);
            driverRepository.save(driver);
        }

        return "orderlist";
    }



}
