package ru.heumn.rest.repos;


import org.springframework.data.repository.CrudRepository;
import ru.heumn.rest.domain.Driver;

public interface DriverRepos extends CrudRepository<Driver, Long> {
}
