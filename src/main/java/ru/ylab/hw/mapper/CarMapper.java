package ru.ylab.hw.mapper;

import org.mapstruct.Mapper;
import ru.ylab.hw.dto.CarDTO;
import ru.ylab.hw.entity.Car;

@Mapper
public interface CarMapper {

    Car toEntity(CarDTO carDTO);
}
