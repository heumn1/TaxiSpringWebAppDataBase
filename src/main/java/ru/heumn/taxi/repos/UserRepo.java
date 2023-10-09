package ru.heumn.taxi.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.heumn.taxi.domain.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByNumber(String number);
}
