package ru.heumn.taxi.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Trip;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, Long> {
    List<Trip> findByDriver_IdIsNull();

    List<Trip> findByDriver_IdIsNullAndType(String type);
}
