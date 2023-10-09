package ru.heumn.taxi.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Car;
import ru.heumn.taxi.domain.Passenger;

public interface CarRepos extends CrudRepository<Car, Long> {
    Passenger getFirstBy();
}
