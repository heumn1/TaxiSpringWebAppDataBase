package ru.heumn.taxi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull(message = "указать цену")
    @Min(value = 150, message = "Минимальная цена дожна быть 150 рублей")
    @Max(value = 2000, message = "Максимальная цена не должна превыщать 2000 рублей")
    Integer price;

    Boolean active;

    String status;

    @Column(name = "type_car")
    @NotEmpty(message = "Вы должны выбрать тип заказываемого вами такси")
    String type;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "address_start_trip")
    String addressStart;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Column(name = "address_finish_trip")
    String addressFinish;

    @NotEmpty(message = "Вы должны выбрать способо оплаты")
    @Column(name = "payment_method")
    String paymentMethod;
}
