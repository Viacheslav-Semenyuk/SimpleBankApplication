package com.example.simple.repository;

import com.example.simple.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface HistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
