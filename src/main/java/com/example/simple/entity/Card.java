package com.example.simple.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cards")
@ToString
public class Card extends BaseEntity {


    @Column
    private BigDecimal balance = BigDecimal.ZERO;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    private Set<TransactionHistory> transactionHistory;


}
