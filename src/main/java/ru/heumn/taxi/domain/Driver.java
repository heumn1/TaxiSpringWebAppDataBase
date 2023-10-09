package ru.heumn.taxi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String FirstName;

    @Column(name = "Last_Name")
    String LastName;

    @ManyToMany
    @JsonManagedReference
    List<Car> car = new ArrayList<>();

    @Column(name = "active_order")
    boolean activeOrder;
}
