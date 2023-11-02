package ru.heumn.taxi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@ToString
@Table(name = "usr")
public class User implements UserDetails {

    //id пользователя
    @Id
    @GeneratedValue(strategy  = GenerationType.SEQUENCE)
    private Long id;

    //Поле никнейма пользователя
    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @Size(min = 2, max = 30, message = "Ошибка! Ваше имя не должен быть меньше 2 и быть больше 30")
    @Column(unique = true)
    private String username;

    //Поле пароля пользователя
    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    private String password;

    //Если ли доступ к системе у пользовтеля
    private boolean active;

    //Поле электронной почты пользователя
    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @Column(unique = true)
    private String email;

    //Поле номера телефона
    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @Size(min = 11, max = 11, message = "Ошибка в поле number")
    @Column(unique = true)
    private String number;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

