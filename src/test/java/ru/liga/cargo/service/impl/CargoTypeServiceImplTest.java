package ru.liga.cargo.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.cargo.entity.CargoType;
import ru.liga.cargo.entity.dto.CargoTypeDto;
import ru.liga.cargo.exception.CargoTypeNotFoundException;
import ru.liga.cargo.mapper.CargoTypeMapper;
import ru.liga.cargo.repository.CargoTypeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CargoTypeServiceImplTest {
    @Mock
    private CargoTypeRepository cargoTypeRepository;

    @Mock
    private CargoTypeMapper cargoTypeMapper;

    @InjectMocks
    private CargoTypeServiceImpl cargoTypeService;

    private CargoType cargoType;
    private CargoTypeDto cargoTypeDto;

    @BeforeEach
    void setUp() {
        cargoType = new CargoType(1L, "Type1", "Representation1");
        cargoTypeDto = new CargoTypeDto(1L, "Type1", "Representation1");
    }

    @Test
    void findAll_ValidState_ReturnsListOfCargoTypeDtos() {
        when(cargoTypeRepository.findAll()).thenReturn(List.of(cargoType));
        when(cargoTypeMapper.toDto(any(CargoType.class))).thenReturn(cargoTypeDto);

        List<CargoTypeDto> result = cargoTypeService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Type1");
        verify(cargoTypeRepository).findAll();
        verify(cargoTypeMapper).toDto(cargoType);
    }

    @Test
    void findByNameIn_ValidNames_ReturnsListOfCargoTypes() {
        List<String> names = List.of("Type1");
        when(cargoTypeRepository.findByNameIn(names)).thenReturn(List.of(cargoType));

        List<CargoType> result = cargoTypeService.findByNameIn(names);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Type1");
        verify(cargoTypeRepository).findByNameIn(names);
    }

    @Test
    void findById_ValidId_ReturnsCargoTypeDto() {
        when(cargoTypeRepository.findById(1L)).thenReturn(Optional.of(cargoType));
        when(cargoTypeMapper.toDto(any(CargoType.class))).thenReturn(cargoTypeDto);

        CargoTypeDto result = cargoTypeService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Type1");
        verify(cargoTypeRepository).findById(1L);
        verify(cargoTypeMapper).toDto(cargoType);
    }

    @Test
    void findById_InvalidId_ThrowsCargoTypeNotFoundException() {
        when(cargoTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cargoTypeService.findById(1L))
                .isInstanceOf(CargoTypeNotFoundException.class)
                .hasMessageContaining("Cargo type with ID 1 not found");
        verify(cargoTypeRepository).findById(1L);
    }

    @Test
    void save_ValidCargoTypeDto_ReturnsCargoTypeDto() {
        when(cargoTypeMapper.toEntity(any(CargoTypeDto.class))).thenReturn(cargoType);
        when(cargoTypeRepository.save(any(CargoType.class))).thenReturn(cargoType);
        when(cargoTypeMapper.toDto(any(CargoType.class))).thenReturn(cargoTypeDto);

        CargoTypeDto result = cargoTypeService.save(cargoTypeDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Type1");
        verify(cargoTypeMapper).toEntity(cargoTypeDto);
        verify(cargoTypeRepository).save(cargoType);
        verify(cargoTypeMapper).toDto(cargoType);
    }

    @Test
    void update_ValidIdAndCargoTypeDto_ReturnsUpdatedCargoTypeDto() {
        CargoTypeDto updatedCargoTypeDto = new CargoTypeDto(1L, "UpdatedType", "UpdatedRepresentation");
        CargoType updatedCargoType = new CargoType(1L, "UpdatedType", "UpdatedRepresentation");
        when(cargoTypeRepository.findById(1L)).thenReturn(Optional.of(cargoType));
        when(cargoTypeRepository.save(any(CargoType.class))).thenReturn(updatedCargoType);
        when(cargoTypeMapper.toDto(any(CargoType.class))).thenReturn(updatedCargoTypeDto);

        CargoTypeDto result = cargoTypeService.update(1L, updatedCargoTypeDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("UpdatedType");
        assertThat(result.getRepresentation()).isEqualTo("UpdatedRepresentation");
        verify(cargoTypeRepository).findById(1L);
        verify(cargoTypeRepository).save(any(CargoType.class));
        verify(cargoTypeMapper).toDto(updatedCargoType);
    }

    @Test
    void deleteById_ValidId_CallsRepositoryDeleteById() {
        cargoTypeService.deleteById(1L);

        verify(cargoTypeRepository).deleteById(1L);
    }
}