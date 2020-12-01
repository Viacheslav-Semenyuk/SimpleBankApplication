package com.example.simple.repository;

import com.example.simple.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByUsername(String username);

    boolean existsByUsername(String username);

}
