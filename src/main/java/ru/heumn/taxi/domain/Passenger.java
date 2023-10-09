package ru.heumn.taxi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends Car {

    @Column
    String ABAGBAA;
}
