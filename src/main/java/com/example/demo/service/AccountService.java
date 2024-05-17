package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AccountService {
    void saveUser(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    List<User> userList();
    Role findUserRoleByName(String role);
    Role saveRole(Role role);
    User updateUser(User user, Map<String,String> request);
    User findById(Long id);
    void deleteUser(User user);
    void resetPassword(User user);
    List<User> getUserListByUsername(String username);
    User simpleSave(User user);
    User saveUser(String name, String username, String email);
    void updateUserPassword(User appUser,String newPassword);
    String saveUserImage(MultipartFile multipartFile,Long userImageId);
}
















