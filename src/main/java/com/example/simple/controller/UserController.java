package com.example.simple.controller;

import com.example.simple.dto.UserDto;
import com.example.simple.entity.User;
import com.example.simple.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Setter(onMethod = @__({@Autowired}))
@RestController
@RequestMapping("api/user")
public class UserController {

    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<User> getCard(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<UserDto> updateProfile(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.updateProfile(userDto), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<UserDto> updatePassword(@RequestBody @Valid UserDto userDto, Principal principal) {

        return new ResponseEntity<>(userService.updatePassword(principal.getName(), userDto), HttpStatus.OK);
    }


    @GetMapping("/profile")
    @ResponseBody
    public ResponseEntity<UserDto> profile(Principal principal) {
        return new ResponseEntity<>(userService.profile(principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {

        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

}
