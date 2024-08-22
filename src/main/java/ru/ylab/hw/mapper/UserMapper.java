package ru.ylab.hw.mapper;

import ru.ylab.hw.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.ylab.hw.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
