package ru.heumn.taxi.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

    Car findByDriver_Id(Long id);
}
