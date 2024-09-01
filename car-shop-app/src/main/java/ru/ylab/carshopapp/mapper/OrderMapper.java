package ru.ylab.carshopapp.mapper;

import ru.ylab.carshopapp.dto.OrderDTO;
import org.mapstruct.Mapper;
import ru.ylab.carshopapp.entity.Order;

@Mapper
public interface OrderMapper {

    OrderDTO toDTO(Order order);
    Order toEntity(OrderDTO orderDTO);
}
