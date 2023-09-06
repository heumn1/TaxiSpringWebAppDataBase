package ru.heumn.rest.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    Driver driver;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long id;
}
