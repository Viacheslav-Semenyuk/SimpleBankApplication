package com.example.simple.controller;


import com.example.simple.dto.CardDto;
import com.example.simple.dto.TransferDto;
import com.example.simple.dto.UserDto;
import com.example.simple.entity.Card;
import com.example.simple.entity.TransactionHistory;
import com.example.simple.service.Impl.CardServiceImpl;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Setter(onMethod = @__({@Autowired}))
@RestController
@RequestMapping("api/card")
public class CardController {


    private CardServiceImpl cardService;


    @GetMapping("{id}")
    public ResponseEntity<Card> getCard(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cardService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid CardDto cardDto) {
        cardService.save(cardDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<CardDto> update(@RequestBody @Valid CardDto cardDto) {

        return new ResponseEntity<>(cardService.update(cardDto), HttpStatus.OK);
    }

    @PostMapping("/created")
    public ResponseEntity<?> newCard(@RequestBody @Valid UserDto userDto) {
        cardService.newCard(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        cardService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/transfer")
    public void transfer(@RequestBody @Valid TransferDto transferDto) {

        cardService.transfer(transferDto.getFromCard(), transferDto.getToCard(), transferDto.getAmount());
    }

    @PostMapping("/history/{id}")
    public ResponseEntity<Set<TransactionHistory>> history(@PathVariable Long id) {
        return new ResponseEntity<>(cardService.cardHistory(id), HttpStatus.OK);
    }

}
