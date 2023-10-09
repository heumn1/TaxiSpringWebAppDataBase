package ru.heumn.taxi.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.UserRepo;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepo.findByUsername(name);
    }

    public Boolean addUser(User user){
        if(user.equals(userRepo.findByNumber(user.getNumber()))){
            return false;
        }
        else {
            userRepo.save(user);
        }
        return true;
    }

}
