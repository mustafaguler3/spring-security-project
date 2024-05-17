package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;

import java.util.List;

public interface AccountService {
    void saveUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> userList();
    Role findUserRoleByName(String role);
    Role saveRole(Role role);
    void updateUser(User user);
    User findById(Long id);
    void deleteUser(User user);
    void resetPassword(User user);
    List<User> getUserListByUsername(String username);
    void simpleSave(User user);
}
















