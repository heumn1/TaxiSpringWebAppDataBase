package ru.heumn.rest.controllers;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.heumn.rest.domain.Car;
import ru.heumn.rest.domain.Driver;
import ru.heumn.rest.domain.Passenger;
import ru.heumn.rest.repos.CarRepos;
import ru.heumn.rest.repos.DriverRepos;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ydalipoton {

    @Autowired
    private CarRepos carRepos;

    @Autowired
    private DriverRepos driverRepos;

    @GetMapping("/")
    public Passenger deleretete(){

        System.out.println("Начало записи");

        Passenger passenger = new Passenger();
        passenger.setABAGBAA("haha");
        passenger.setModel("CARCARICH");
        passenger.setName("nameofcar");
        passenger.setHeight("16");
        passenger.setSeats("6");

        carRepos.save(passenger);
        System.out.println("Конец записи");

        return passenger;
    }

    @GetMapping("/re")
    public Iterable<Car> passengerList() {
        return carRepos.findAll();
    }
    @GetMapping("/test1")
    public Passenger testhiber() {
        Configuration con = new Configuration().configure()
                .addAnnotatedClass(Passenger.class);
        StandardServiceRegistryBuilder sBilder = new StandardServiceRegistryBuilder()
                .applySettings(con.getProperties());
        SessionFactory sessionFactory = con.buildSessionFactory(sBilder.build());
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Passenger passenger = session.get(Passenger.class, 1);
        passenger.setName("пфпфпф");
        session.getTransaction().commit();

        Passenger passenger1 = carRepos.getFirstBy();
        return passenger1;
    }
    @GetMapping("/test2")
    public Passenger testjpa() {

        Passenger passenger = carRepos.getFirstBy();
        passenger.setName("6151");
        carRepos.save(passenger);

        Passenger passenger1 = carRepos.getFirstBy();
        return passenger1;
    }

    @GetMapping("/test3")
    public Iterable<Driver> testonetomany1() {

        Passenger passenger1 = new Passenger();
        passenger1.setABAGBAA("abama1");
        passenger1.setModel("toyota");
        passenger1.setName("nameofcarONE");
        passenger1.setHeight("10");
        passenger1.setSeats("5");

        Passenger passenger2 = new Passenger();
        passenger2.setABAGBAA("Trump");
        passenger2.setModel("hyndai");
        passenger2.setName("nameofcarTWO");
        passenger2.setHeight("20");
        passenger2.setSeats("10");

        Driver driver = new Driver();
        driver.setFirstName("ОЛЕГарх");
        driver.setLastName("Flastname2");

        List<Car> cars = new ArrayList<>();
        cars.add(passenger1);
        cars.add(passenger2);

        carRepos.save(passenger1);
        carRepos.save(passenger2);
        driverRepos.save(driver);

        return driverRepos.findAll();
    }
    @GetMapping("/get")
    public Iterable<Driver> get1() {

        return driverRepos.findAll();
    }
    @GetMapping("/get2")
    public Iterable<Car> get2() {

        return carRepos.findAll();
    }

    @GetMapping("/test4")
    public Iterable<Driver> testrwagf(){

        Passenger passenger1 = new Passenger();
        passenger1.setABAGBAA("abama1");
        passenger1.setModel("toyota");
        passenger1.setName("nameofcarONE");
        passenger1.setHeight("10");
        passenger1.setSeats("5");

        Passenger passenger2 = new Passenger();
        passenger2.setABAGBAA("Trump");
        passenger2.setModel("hyndai");
        passenger2.setName("nameofcarTWO");
        passenger2.setHeight("20");
        passenger2.setSeats("10");

        Driver driver = new Driver();
        driver.setFirstName("ОЛЕГарх");
        driver.setLastName("Flastname2");

        List<Car> cars = new ArrayList<>();
        cars.add(passenger1);
        cars.add(passenger2);
        driver.setCars(cars);

        Configuration con = new Configuration().configure()
                .addAnnotatedClass(Passenger.class)
                .addAnnotatedClass(Driver.class);
        StandardServiceRegistryBuilder sBilder = new StandardServiceRegistryBuilder()
                .applySettings(con.getProperties());
        SessionFactory sessionFactory = con.buildSessionFactory(sBilder.build());
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.save(passenger1);
        session.save(passenger2);
        session.save(driver);



        session.getTransaction().commit();

        return driverRepos.findAll();
    }

    @GetMapping("/testmeme")
    public Passenger fgdgf() {

        Configuration con = new Configuration().configure()
                .addAnnotatedClass(Passenger.class)
                .addAnnotatedClass(Driver.class);
        StandardServiceRegistryBuilder sBilder = new StandardServiceRegistryBuilder()
                .applySettings(con.getProperties());
        SessionFactory sessionFactory = con.buildSessionFactory(sBilder.build());
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Passenger passenger = session.get(Passenger.class, 1);
        Driver driver = session.get(Driver.class,102);

        System.out.println(passenger.getId() + "____" + passenger.getDriver().getId());

        System.out.println(driver.getId());

        passenger.setDriver(driver);

        session.getTransaction().commit();



        return carRepos.getFirstBy();
    }
}
