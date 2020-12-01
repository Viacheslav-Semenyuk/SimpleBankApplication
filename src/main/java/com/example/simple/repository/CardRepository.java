package com.example.simple.repository;

import com.example.simple.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Set<Card> findAllByUser_Id(Long id);
}
