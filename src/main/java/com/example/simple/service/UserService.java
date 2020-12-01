package com.example.simple.service;

import com.example.simple.dto.UserDto;
import com.example.simple.entity.User;

import java.util.List;

public interface UserService {

    User getById(Long id);

    void save(User user);

    UserDto updateProfile(UserDto userDto);

    UserDto updatePassword(String username, UserDto userDto);

    UserDto profile(String username);

    void delete(Long id);

    List<UserDto> getAll();

}
