package ru.heumn.taxi.repos;


import org.springframework.data.repository.CrudRepository;
import ru.heumn.taxi.domain.Driver;

public interface DriverRepos extends CrudRepository<Driver, Long> {

}
