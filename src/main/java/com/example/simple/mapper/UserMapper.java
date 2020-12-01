package com.example.simple.mapper;


import com.example.simple.dto.CardDto;
import com.example.simple.dto.UserDto;
import com.example.simple.entity.Card;
import com.example.simple.entity.User;
import com.example.simple.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Setter(onMethod = @__({@Autowired}))
@Component
public class UserMapper {


    private CardMapper cardMapper;

    private UserService userService;

    public User mapToEntity(UserDto userDto) {
        User user = userDto.getId() == null
                ? new User() : userService.getById(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setBirthday(userDto.getBirthday());
        Set<Card> cardDto = userDto.getCard().stream()
                .map(card -> cardMapper.mapToEntity(card)).collect(Collectors.toSet());
        user.setCards(cardDto);
        return user;
    }

    public UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();

        Set<CardDto> cardDto = user.getCards().stream()
                .map(card -> cardMapper.mapToDto(card)).collect(Collectors.toSet());

        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setLastName(user.getLastName());
        userDto.setFirstName(user.getFirstName());
        userDto.setBirthday(user.getBirthday());
        userDto.setCard(cardDto);

        return userDto;
    }

}
