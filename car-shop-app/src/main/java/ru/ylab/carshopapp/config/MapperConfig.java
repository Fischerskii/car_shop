package ru.ylab.carshopapp.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ylab.carshopapp.mapper.CarMapper;
import ru.ylab.carshopapp.mapper.OrderMapper;

@Configuration
public class MapperConfig {

    @Bean
    public CarMapper carMapper() {
        return Mappers.getMapper(CarMapper.class);
    }

    @Bean
    public OrderMapper orderMapper() {
        return Mappers.getMapper(OrderMapper.class);
    }
}
