package ru.liga.truck.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.truck.entity.TruckType;
import ru.liga.truck.entity.dto.TruckTypeDto;
import ru.liga.truck.exception.TruckTypeNotFoundException;
import ru.liga.truck.mapper.TruckTypeMapper;
import ru.liga.truck.repository.TruckTypeRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TruckTypeServiceImplTest {
    @Mock
    private TruckTypeRepository truckTypeRepository;

    @Mock
    private TruckTypeMapper truckTypeMapper;

    @InjectMocks
    private TruckTypeServiceImpl truckTypeService;

    @Test
    void findAll_ExistingTruckTypes_ReturnsListOfTruckTypeDtos() {
        TruckType truckType = new TruckType(1L, "Type1", 2, 3);
        TruckTypeDto truckTypeDto = new TruckTypeDto(1L, "Type1", 2, 3);

        when(truckTypeRepository.findAll()).thenReturn(Collections.singletonList(truckType));
        when(truckTypeMapper.toDto(truckType)).thenReturn(truckTypeDto);

        List<TruckTypeDto> result = truckTypeService.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(truckTypeDto);
    }

    @Test
    void findByName_ExistingName_ReturnsTruckTypeDto() {
        TruckType truckType = new TruckType(1L, "Type1", 2, 3);
        TruckTypeDto truckTypeDto = new TruckTypeDto(1L, "Type1", 2, 3);
        when(truckTypeRepository.findByName("Type1")).thenReturn(truckType);
        when(truckTypeMapper.toDto(truckType)).thenReturn(truckTypeDto);

        TruckTypeDto result = truckTypeService.findByName("Type1");

        assertThat(result).isEqualTo(truckTypeDto);
    }

    @Test
    void findById_ExistingId_ReturnsTruckTypeDto() {
        TruckType truckType = new TruckType(1L, "Type1", 2, 3);
        TruckTypeDto truckTypeDto = new TruckTypeDto(1L, "Type1", 2, 3);
        when(truckTypeRepository.findById(1L)).thenReturn(Optional.of(truckType));
        when(truckTypeMapper.toDto(truckType)).thenReturn(truckTypeDto);

        TruckTypeDto result = truckTypeService.findById(1L);

        assertThat(result).isEqualTo(truckTypeDto);
    }

    @Test
    void findById_NonExistingId_ThrowsTruckTypeNotFoundException() {
        when(truckTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> truckTypeService.findById(1L))
                .isInstanceOf(TruckTypeNotFoundException.class)
                .hasMessageContaining("1");
    }

    @Test
    void save_ValidTruckTypeDto_ReturnsSavedTruckTypeDto() {
        TruckType truckType = new TruckType(null, "Type1", 2, 3);
        TruckType savedTruckType = new TruckType(1L, "Type1", 2, 3);
        TruckTypeDto truckTypeDto = new TruckTypeDto(null, "Type1", 2, 3);
        TruckTypeDto savedTruckTypeDto = new TruckTypeDto(1L, "Type1", 2, 3);

        when(truckTypeMapper.toEntity(truckTypeDto)).thenReturn(truckType);
        when(truckTypeRepository.save(truckType)).thenReturn(savedTruckType);
        when(truckTypeMapper.toDto(savedTruckType)).thenReturn(savedTruckTypeDto);

        TruckTypeDto result = truckTypeService.save(truckTypeDto);

        assertThat(result).isEqualTo(savedTruckTypeDto);
    }

    @Test
    void update_ExistingId_ReturnsUpdatedTruckTypeDto() {
        TruckType truckTypeFromDb = new TruckType(1L, "Type1", 2, 3);
        TruckTypeDto updatedTruckTypeDto = new TruckTypeDto(1L, "UpdatedType", 4, 5);
        TruckType updatedTruckType = new TruckType(1L, "UpdatedType", 4, 5);

        when(truckTypeRepository.findById(1L)).thenReturn(Optional.of(truckTypeFromDb));
        when(truckTypeRepository.save(truckTypeFromDb)).thenReturn(updatedTruckType);
        when(truckTypeMapper.toDto(updatedTruckType)).thenReturn(updatedTruckTypeDto);

        TruckTypeDto result = truckTypeService.update(1L, updatedTruckTypeDto);

        assertThat(result).isEqualTo(updatedTruckTypeDto);
        assertThat(truckTypeFromDb.getName()).isEqualTo("UpdatedType");
        assertThat(truckTypeFromDb.getWidth()).isEqualTo(4);
        assertThat(truckTypeFromDb.getHeight()).isEqualTo(5);
    }

    @Test
    void deleteById_ExistingId_DeletesTruckType() {
        Long id = 1L;

        truckTypeService.deleteById(id);

        verify(truckTypeRepository, times(1)).deleteById(id);
    }
}