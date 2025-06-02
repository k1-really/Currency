package com.kireally.Currency.mapper;

import com.kireally.Currency.model.entity.user.User;
import com.kireally.Currency.model.dto.user.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
