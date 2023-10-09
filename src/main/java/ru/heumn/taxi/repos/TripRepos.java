package ru.heumn.taxi.repos;

import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Trip;

public interface TripRepos extends CrudRepository<Trip, Long> {


}
