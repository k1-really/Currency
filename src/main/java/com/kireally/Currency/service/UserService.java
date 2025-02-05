package com.kireally.Currency.service;

import com.kireally.Currency.model.user.User;
import com.kireally.Currency.web.dto.user.UserDto;

public interface UserService {
    User getById(Long id);
    User getByEmail(String email);
    User update(User user);
    User create(User user);
    void delete(Long id);
    User getByNickname(String nickname);
}
