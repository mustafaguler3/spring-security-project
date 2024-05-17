package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    @Query("select u from User u where u.id=:id")
    User findUserById(@Param("id") Long id);
    List<User> findByUsernameContaining(String username);


    Role findUserRoleByName(String role);
}
















