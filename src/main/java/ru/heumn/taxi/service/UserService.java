package ru.heumn.taxi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.heumn.taxi.domain.User;
import ru.heumn.taxi.repos.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByUsername(name);
    }

    public Boolean addUser(User user){
        if(user.equals(userRepository.findByNumber(user.getNumber()))){
            return false;
        }
        else {
            userRepository.save(user);
        }
        return true;
    }

}
