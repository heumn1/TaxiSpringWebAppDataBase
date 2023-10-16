package ru.heumn.taxi.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Car {
    @Column(name = "number_car")
    String number;

    @Column(name = "model_car")
    String model;

    @Column(name = "height_car")
    String height;

    @Column(name = "seats_car")
    String seats;

    @Column(name = "type_car")
    String type;

    @OneToOne()
    Driver driver;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
