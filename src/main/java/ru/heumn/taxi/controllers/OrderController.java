package ru.heumn.taxi.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.heumn.taxi.ChatMessage;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.CarRepository;
import ru.heumn.taxi.repos.DriverRepository;
import ru.heumn.taxi.repos.TripRepository;
import ru.heumn.taxi.repos.UserRepository;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping()
    private String main(Model model){

        List<String> cars = new ArrayList<>();
        cars.add("Эконом");
        cars.add("Люкс");
        cars.add("Грузовой");

        model.addAttribute("cars", cars);

        model.addAttribute("Trip", new Trip());
        return "order";
    }

    @PostMapping()
    public String order(@ModelAttribute("Trip") @Valid Trip trip, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors())
        {
            return "order";
        }
        User user = userRepository.findByUsername(principal.getName());

        trip.setDriver(null);
        trip.setUser(user);
        trip.setStatus("Поиск");

        tripRepository.save(trip);

        return "redirect:/order/waiting";
    }

    @GetMapping("/waiting")
    public String waiting(Model model, Principal principal){

        Trip trip = tripRepository.findByUserAndActiveIsNull(userRepository.findByUsername(principal.getName()));

        model.addAttribute("Trip", trip);
        return "waiting";
    }

    @PreAuthorize("hasAuthority('DRIVER')")
    @GetMapping("/orderlist")
    public String orderlist(Model model, @ModelAttribute("Trip") Trip trip, Principal principal){

        if(driverRepository.findByIdUser(userRepository.findByUsername(principal.getName())).isActiveOrder() )
        {
            return "redirect:/driver/activeorder";
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

    @PreAuthorize("hasAuthority('DRIVER')")
    @PostMapping("/orderlist/{id}")
    public String orderSubmit(@PathVariable Long id, Principal principal,@Payload ChatMessage chatMessage){
        Optional<Trip> trip = tripRepository.findById(id);

        if(trip.isPresent())
        {
            trip.get().setDriver(driverRepository.findByIdUser(userRepository.findByUsername(principal.getName())));
            trip.get().setActive(true);
            trip.get().setStatus("В пути");

            tripRepository.save(trip.get());

            Driver driver = driverRepository.findByIdUser(userRepository.findByUsername(principal.getName()));
            driver.setActiveOrder(true);
            driverRepository.save(driver);


            chatMessage.setSender(trip.get().getUser().getUsername());
            chatMessage.setContent("Водитель принял заказ! \n" + carRepository.findByDriver_Id(driver.getId()).toString());
            chatMessage.setType("RESPONSE");

            this.template.convertAndSend("/topic/public", chatMessage);

        }

        return "redirect:/driver/activeorder";
    }

    @PostMapping("/waiting")
    public String orderCancel(Principal principal, @Payload ChatMessage chatMessage){

        List<Trip> trip = tripRepository.findByUser(userRepository.findByUsername(principal.getName()));

        trip.get(0).setStatus("Отменен");
        trip.get(0).setActive(false);

        if(trip.get(0).getDriver() != null)
        {
            chatMessage.setSender(userRepository.findById(trip.get(0).getDriver().getIdUser().getId()).get().getUsername());
            trip.get(0).getDriver().setActiveOrder(false);
        }


        chatMessage.setContent("Пользователь отменил заказ!");
        chatMessage.setType("RESPONSE");

        this.template.convertAndSend("/topic/public", chatMessage);

        tripRepository.save(trip.get(0));
        return "redirect:/account/history";
    }

}
