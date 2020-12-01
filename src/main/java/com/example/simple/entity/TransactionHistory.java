package com.example.simple.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction_history")
@EntityListeners(AuditingEntityListener.class)
public class TransactionHistory extends BaseEntity {


    @Column
    private String history;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @CreatedDate
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private Date createdAt;

}
