package ru.ylab.carshopapp.mapper;

import org.mapstruct.Mapper;
import ru.ylab.carshopapp.dto.CarDTO;
import ru.ylab.carshopapp.entity.Car;

@Mapper
public interface CarMapper {

    Car toEntity(CarDTO carDTO);
}
