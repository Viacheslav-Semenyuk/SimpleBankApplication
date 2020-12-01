package com.example.simple.mapper;

import com.example.simple.dto.CardDto;
import com.example.simple.entity.Card;
import com.example.simple.service.CardService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter(onMethod = @__({@Autowired}))
@Component
public class CardMapper {

    private CardService cardService;

    public Card mapToEntity(CardDto cardDto) {
        Card card = cardDto.getId() == null
                ? new Card() : cardService.getById(cardDto.getId());
        card.setBalance(cardDto.getBalance());
        return card;
    }

    public CardDto mapToDto(Card card) {
        CardDto cardDto = new CardDto();

        cardDto.setId(card.getId());
        cardDto.setBalance(card.getBalance());
        cardDto.setTransactionHistory(card.getTransactionHistory());

        return cardDto;
    }
}
