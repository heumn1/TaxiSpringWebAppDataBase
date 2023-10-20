package ru.heumn.taxi.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Trip;
import ru.heumn.taxi.domain.User;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findByDriver_IdIsNull();
    List<Trip> findByDriver_IdIsNullAndType(String type);

    Trip findByDriver_IdAndActiveIsTrue(Long id);

    Trip findByUserAndActiveIsNull(User user);

    Trip findByUserAndActiveIsTrue(User user);
    Trip findByUser(User user);

}
