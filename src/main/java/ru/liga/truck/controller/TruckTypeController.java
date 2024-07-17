package ru.liga.truck.controller;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.liga.truck.entity.dto.TruckTypeDto;
import ru.liga.truck.exception.TruckTypeNotFoundException;
import ru.liga.truck.service.TruckTypeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/truck-types")
public class TruckTypeController {

    private final TruckTypeService truckTypeService;

    @GetMapping
    public ResponseEntity<List<TruckTypeDto>> getAllTruckTypes() {
        return ResponseEntity.ok(truckTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TruckTypeDto> getTruckTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(truckTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TruckTypeDto> createTruckType(@Valid @RequestBody TruckTypeDto truckTypeDto) {
        return ResponseEntity.ok(truckTypeService.save(truckTypeDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TruckTypeDto> updateTruckType(@PathVariable Long id, @RequestBody TruckTypeDto truckTypeDto) {
        return ResponseEntity.ok(truckTypeService.update(id, truckTypeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTruckType(@PathVariable Long id) {
        truckTypeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(TruckTypeNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(TruckTypeNotFoundException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}