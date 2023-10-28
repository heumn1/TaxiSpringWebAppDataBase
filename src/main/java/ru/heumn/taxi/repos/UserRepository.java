package ru.heumn.taxi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.taxi.domain.Role;
import ru.heumn.taxi.domain.User;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByNumber(String number);


}
