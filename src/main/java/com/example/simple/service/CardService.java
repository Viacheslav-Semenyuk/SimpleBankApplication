package com.example.simple.service;

import com.example.simple.dto.CardDto;
import com.example.simple.dto.UserDto;
import com.example.simple.entity.Card;
import com.example.simple.entity.TransactionHistory;
import com.example.simple.exception.EntityNotFoundException;

import java.util.Set;


public interface CardService {

    Card getById(Long id);

    Set<Card> findAllByUser_Id(Long id);

    void newCard(UserDto userDto);

    void save(CardDto cardDto);

    CardDto update(CardDto cardDto);

    void delete(Long id);

    Set<TransactionHistory> cardHistory(Long id);
}
