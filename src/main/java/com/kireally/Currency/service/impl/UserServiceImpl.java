package com.kireally.Currency.service.impl;

import com.kireally.Currency.exception.ResourceNotFoundException;
import com.kireally.Currency.model.entity.enums.Role;
import com.kireally.Currency.model.entity.user.User;
import com.kireally.Currency.repository.UserRepository;
import com.kireally.Currency.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with this id not found"))  ;
    }

    @Transactional(readOnly = true)
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public User getByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(()->new ResourceNotFoundException("User not found"));
    }

    @Transactional
    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User create(User user) {
        if(userRepository.findByNickname(user.getNickname()).isPresent()){
            throw new IllegalStateException("User with this nickname already exists.");
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("Passwords do not match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
