package com.kireally.Currency.mapper;

import com.kireally.Currency.model.user.User;
import com.kireally.Currency.web.dto.user.UserDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
