package com.kireally.Currency.mapper;

import com.kireally.Currency.model.auth.UserDto;
import com.kireally.Currency.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirmation", ignore = true)
    UserDto toDto(User user);
    User toEntity(UserDto userDto);
}
