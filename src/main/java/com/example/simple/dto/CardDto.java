package com.example.simple.dto;

import com.example.simple.entity.TransactionHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString
public class CardDto {

    private Long id;

    private BigDecimal balance = BigDecimal.ZERO;

    private Set<TransactionHistory> transactionHistory;

}
