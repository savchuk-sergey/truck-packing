package ru.liga.cargo.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.cargo.entity.dto.CargoTypeDto;
import ru.liga.cargo.exception.CargoTypeNotFoundException;
import ru.liga.cargo.service.CargoTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/cargo-types")
public class CargoTypeController {
    private final CargoTypeService cargoTypeService;

    @Autowired
    public CargoTypeController(CargoTypeService cargoTypeService) {
        this.cargoTypeService = cargoTypeService;
    }

    @GetMapping
    public ResponseEntity<List<CargoTypeDto>> getAllCargoTypes() {
        return ResponseEntity.ok(cargoTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoTypeDto> getCargoTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(cargoTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CargoTypeDto> createCargoType(@Valid @RequestBody CargoTypeDto cargoTypeDto) {
        return ResponseEntity.ok(cargoTypeService.save(cargoTypeDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CargoTypeDto> updateCargoType(@PathVariable Long id, @RequestBody CargoTypeDto cargoTypeDto) {
        return ResponseEntity.ok(cargoTypeService.update(id, cargoTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargoType(@PathVariable Long id) {
        cargoTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(CargoTypeNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(CargoTypeNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}