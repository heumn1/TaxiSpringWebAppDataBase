package ru.heumn.taxi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;


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

    @NotNull(message = "укажите цену")
    @Min(value = 150, message = "Ошибка! Минимальная цена дожна быть 150 рублей")
    @Max(value = 2500, message = "Ошибка! Максимальная цена не должна превыщать 2000 рублей")
    Integer price;

    Boolean active;

    String status;

    @Column(name = "type_car")
    @NotEmpty(message = "Ошибка! Вы должны выбрать тип заказываемого вами такси")
    String type;

    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @Column(name = "address_start_trip")
    String addressStart;

    @NotEmpty(message = "Ошибка! Поле не должно быть пустым")
    @Column(name = "address_finish_trip")
    String addressFinish;

    @NotEmpty(message = "Ошибка! Вы должны выбрать способо оплаты")
    @Column(name = "payment_method")
    String paymentMethod;

//    @Column(name = "Date_Trip")
//    @Temporal(TemporalType.DATE)
//    Date Date;
//
//    @Column(name = "Time_Start")
//    @Temporal(TemporalType.TIME)
//    Date TimeStart;
//
//    @Column(name = "Time_Finish")
//    @Temporal(TemporalType.TIME)
//    Date TimeFinish;
}
