package ru.ylab.hw.mapper;

import ru.ylab.hw.dto.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.hw.entity.Car;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    CarDTO toDTO(Car car);
    Car toEntity(CarDTO carDTO);
}
