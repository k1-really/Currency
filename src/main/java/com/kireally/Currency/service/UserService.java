package com.kireally.Currency.service;

import com.kireally.Currency.model.entity.user.User;

public interface UserService {
    User getById(Long id);
    User getByEmail(String email);
    User update(User user);
    User create(User user);
    void delete(Long id);
    User getByNickname(String nickname);
}
