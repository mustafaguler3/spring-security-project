package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findUserById(Long id);
    List<User> findByUsernameContaining(String username);
}
















