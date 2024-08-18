package ru.ylab.hw.mapper;

import ru.ylab.hw.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.hw.entity.Order;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
}
