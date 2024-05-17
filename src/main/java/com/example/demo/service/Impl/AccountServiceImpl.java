package com.example.demo.service.Impl;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.AccountService;
import com.example.demo.utility.EmailConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private PasswordEncoder passwordEncoder;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private EmailConstructor emailConstructor;
    private JavaMailSender mailSender;

    @Autowired
    public AccountServiceImpl(PasswordEncoder passwordEncoder, AccountRepository accountRepository, RoleRepository roleRepository, EmailConstructor emailConstructor, JavaMailSender mailSender) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.emailConstructor = emailConstructor;
        this.mailSender = mailSender;
    }

    @Override
    public void saveUser(User user) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        accountRepository.save(user);
        mailSender.send(emailConstructor.constructNewUserEmail(user,password));
    }

    @Override
    public User findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public List<User> userList() {
        return accountRepository.findAll();
    }

    @Override
    public Role findUserRoleByName(String role) {
        return roleRepository.findRoleByName(role);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void updateUser(User user) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        accountRepository.save(user);
        mailSender.send(emailConstructor.constructUpateUserProfileEmail(user));
    }

    @Override
    public User findById(Long id) {
        return accountRepository.findUserById(id);
    }

    @Override
    public void deleteUser(User user) {
        accountRepository.delete(user);
    }

    @Override
    public void resetPassword(User user) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        accountRepository.save(user);
        mailSender.send(emailConstructor.constructResetPasswordEmail(user,password));
    }

    @Override
    public List<User> getUserListByUsername(String username) {
        return accountRepository.findByUsernameContaining(username);
    }

    @Override
    public User simpleSave(User user) {
        accountRepository.save(user);
        mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));

        return user;
    }
}
