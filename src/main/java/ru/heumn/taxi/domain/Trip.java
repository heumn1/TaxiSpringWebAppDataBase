package ru.heumn.taxi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Trip {

    @Id
    @GeneratedValue(strategy  = GenerationType.SEQUENCE)
    Integer id;

    @ManyToOne
    User user;

    @ManyToOne
    Driver driver;

    String price;

    @Column(name = "type_car")
    @NotEmpty(message = "Вы должны выбрать тип заказываемого вами такси")
    String type;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "address_trip")
    String address;

    @NotEmpty(message = "Вы должны выбрать способо оплаты")
    @Column(name = "payment_method")
    String paymentMethod;

}
