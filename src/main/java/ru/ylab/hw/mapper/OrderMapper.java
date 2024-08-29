package ru.ylab.hw.mapper;

import ru.ylab.hw.dto.OrderDTO;
import org.mapstruct.Mapper;
import ru.ylab.hw.entity.Order;

@Mapper
public interface OrderMapper {

    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
}
