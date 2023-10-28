package ru.heumn.taxi.repos;


import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Driver;
import ru.heumn.taxi.domain.User;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Driver findByActiveOrderIsTrue();
    Driver findByIdUser(User user);
}
