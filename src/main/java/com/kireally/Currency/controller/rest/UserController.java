package com.kireally.Currency.controller.rest;

import com.kireally.Currency.mapper.UserMapper;
import com.kireally.Currency.model.entity.user.User;
import com.kireally.Currency.service.UserService;
import com.kireally.Currency.model.dto.user.UserDto;
import com.kireally.Currency.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }


    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }


    public UserDto getByNickname(@RequestParam String nickname) {
        return userMapper.toDto(userService.getByNickname(nickname));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
