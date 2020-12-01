package com.example.simple.service.Impl;

import com.example.simple.constant.Constants;
import com.example.simple.dto.CardDto;
import com.example.simple.dto.UserDto;
import com.example.simple.entity.Card;
import com.example.simple.entity.TransactionHistory;
import com.example.simple.exception.EntityNotFoundException;
import com.example.simple.mapper.CardMapper;
import com.example.simple.mapper.UserMapper;
import com.example.simple.repository.CardRepository;
import com.example.simple.repository.HistoryRepository;
import com.example.simple.service.CardService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

import static java.lang.String.format;


@Slf4j
@Service
@Setter(onMethod = @__({@Autowired}))
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private HistoryRepository historyRepository;
    private CardMapper cardMapper;
    private UserMapper userMapper;

    @Override
    public Card getById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MSG, id)));
    }

    @Override
    public Set<Card> findAllByUser_Id(Long id) {
        return cardRepository.findAllByUser_Id(id);
    }

    @Override
    public void save(CardDto cardDto) {
        cardRepository.save(cardMapper.mapToEntity(cardDto));
    }

    @Override
    public CardDto update(CardDto cardDto) {
        TransactionHistory depositHistory = new TransactionHistory();
        Card card = getById(cardDto.getId());

        if (cardDto.getBalance() != null) {
            if (!cardDto.getBalance().equals(BigDecimal.ZERO)) {
                card.setBalance(card.getBalance().add(cardDto.getBalance()));
                depositHistory.setCard(card);
                depositHistory.setHistory("Replenishment " + cardDto.getBalance() + "$");
                historyRepository.save(depositHistory);
            }
        }

        return cardMapper.mapToDto(card);
    }

    @Override
    public void newCard(UserDto userDto) {
        Card card = new Card();
        card.setUser(userMapper.mapToEntity(userDto));
        cardRepository.save(card);
    }

    @Override
    public void delete(Long id) {
        Card deleteHistory = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MSG, id)));
        deleteHistory.setUser(null);
        for (TransactionHistory getHistory : deleteHistory.getTransactionHistory()) {
            historyRepository.delete(getHistory);
        }
        cardRepository.deleteById(id);
    }


    public void transfer(Long fromCardId, Long toCardId, BigDecimal amount) {
        Card fromCard = getById(fromCardId);
        Card toCard = getById(toCardId);

        TransactionHistory fromHistory = new TransactionHistory();
        TransactionHistory toHistory = new TransactionHistory();


        if (amount != null) {
            if (!amount.equals(BigDecimal.ZERO)) {
                if (fromCard.getBalance().compareTo(amount) > 0) {
                    //from history
                    fromCard.setBalance(fromCard.getBalance().subtract(amount));
                    fromHistory.setCard(getById(fromCardId));
                    fromHistory.setHistory("Transferred " + amount + "$" + " to a bank card: " + toCardId);

                    //to history
                    toCard.setBalance(toCard.getBalance().add(amount));
                    toHistory.setCard(getById(toCardId));
                    toHistory.setHistory("Transferred " + amount + "$" + " from a bank card: " + fromCardId);


                    cardRepository.save(fromCard);
                    cardRepository.save(toCard);
                    historyRepository.save(fromHistory);
                    historyRepository.save(toHistory);
                }
            }
        }

    }

    @Override
    public Set<TransactionHistory> cardHistory(Long id) {
        Card history = cardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MSG, id)));
        return history.getTransactionHistory();
    }
}

