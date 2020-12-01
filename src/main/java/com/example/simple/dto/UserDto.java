package com.example.simple.dto;


import com.example.simple.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String newPassword;

    private String email;

    private String lastName;

    private String firstName;

    private String birthday;

    private Set<CardDto> card;

    private Set<Role> roles;
}
