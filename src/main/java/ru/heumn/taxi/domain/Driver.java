package ru.heumn.taxi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.heumn.taxi.repos.CarRepository;

import java.util.ArrayList;
import java.util.List;

@Entity
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
    @Max(value = 20, message = "Имя не должна быть больше 20 символов")
    @Min(value = 2, message = "Имя не должна быть меньше 2 символов")
    String FirstName;


    @Column(name = "Last_Name")
    @Max(value = 20, message = "Фамилия не должна быть больше 20 символов")
    @Min(value = 2, message = "Фамилия не должна быть меньше 2 символов")
    String LastName;

    @OneToOne
    User idUser;


    @Column(name = "active_order")
    boolean activeOrder;

    public Car GetCar(CarRepository carRepository){
       return carRepository.findByDriver_Id(this.id);
    }
}
