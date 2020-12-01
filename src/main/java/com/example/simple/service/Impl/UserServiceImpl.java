package com.example.simple.service.Impl;

import com.example.simple.constant.Constants;
import com.example.simple.dto.UserDto;
import com.example.simple.entity.Card;
import com.example.simple.entity.Role;
import com.example.simple.entity.User;
import com.example.simple.exception.EntityAlreadyExistException;
import com.example.simple.exception.EntityNotFoundException;
import com.example.simple.mapper.UserMapper;
import com.example.simple.repository.CardRepository;
import com.example.simple.repository.RoleRepository;
import com.example.simple.repository.UserRepository;
import com.example.simple.service.CardService;
import com.example.simple.service.UserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;


@Slf4j
@Service
@Setter(onMethod = @__({@Autowired}))
public class UserServiceImpl implements UserService {

    private CardService cardService;

    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;

    private RoleRepository roleRepository;
    private CardRepository cardRepository;


    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MSG, id)));
    }


    @Override
    public void save(User user) {

        if(userRepository.existsByUsername(user.getUsername())){
            throw new EntityAlreadyExistException(
                    format(Constants.USER_BY_USER_ALREADY_EXISTS_EXCEPTION_MSG,
                           user.getUsername()));
        }

        Role roleUser = roleRepository.findByName("ROLE_USER");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleUser);
        user.setRoles(userRoles);

        Card card = new Card();
        card.setUser(user);
        cardRepository.save(card);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.mapToDto(userRepository.save(user));
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        User user = userRepository.save(userMapper.mapToEntity(userDto));
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto updatePassword(String username, UserDto userDto) {
        User user = userRepository.findByUsername(username);

        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
        }
        return userMapper.mapToDto(userRepository.save(user));
    }


    @Override
    public UserDto profile(String username) {
        return userMapper.mapToDto(userRepository.findByUsername(username));
    }

    @Override
    public void delete(Long id) {
        Set<Card> card = cardService.findAllByUser_Id(id);
        for (Card cardId : card) {
            cardService.delete(cardId.getId());
        }
        userRepository.deleteById(id);
    }


    @Override
    public List<UserDto> getAll() {

        return userRepository.findAll().stream()
                .map(user -> userMapper.mapToDto(user))
                .collect(Collectors.toList());
    }


}
