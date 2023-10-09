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
public abstract class Car {
    @Column(name = "name_car")
    String name;

    @Column(name = "model_car")
    String model;

    @Column(name = "height_car")
    String height;

    @Column(name = "seats_car")
    String seats;

    @ManyToMany(mappedBy = "car")
    @JsonBackReference
    List<Driver> driver = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long id;
}
