package ru.heumn.rest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.rest.domain.Car;
import ru.heumn.rest.domain.Passenger;

public interface CarRepos extends CrudRepository<Car, Long> {
    Passenger getFirstBy();
}
