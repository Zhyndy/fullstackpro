package ru.javabegin.hibernate.lab05.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javabegin.hibernate.lab05.domain.Car;
import ru.javabegin.hibernate.lab05.domain.CarRepository;


@RestController
public class CarController {
    private final CarRepository repository;

    public CarController(CarRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cars")
    public Iterable<Car> getCars() {
        return repository.findAll();
    }
}

