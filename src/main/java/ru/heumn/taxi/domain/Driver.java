package ru.heumn.taxi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.taxi.repos.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Driver {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "First_Name")
    @Size(min = 2, max = 30, message = "Ошибка! Имя не должен быть меньше 2 и быть больше 30")
    String firstName;

    @Column(name = "Last_Name")
    @Size(min = 2, max = 30, message = "Ошибка! Фамилия не должен быть меньше 2 и быть больше 30")
    String lastName;

    @Column(name = "Patronymic")
    @Size(min = 2, max = 30, message = "Ошибка! Отчество не должен быть меньше 2 и быть больше 30")
    String patronymic;

    @OneToOne
    User idUser;

    @Column(name = "active_order")
    boolean activeOrder;

    @Column(name = "present_driver")
    boolean present;

    public Car GetCar(CarRepository carRepository){
       return carRepository.findByDriver_Id(this.id);
    }
}
