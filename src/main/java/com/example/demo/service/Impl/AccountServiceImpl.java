package com.example.demo.service.Impl;

import com.example.demo.contant.Constants;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserRole;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.AccountService;
import com.example.demo.utility.EmailConstructor;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Transactional
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
    public User updateUser(User user, Map<String,String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String bio = request.get("bio");

        user.setName(name);
        user.setEmail(email);
        user.setBio(bio);
        accountRepository.save(user);
        mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));

        return user;
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

    @Override
    public User saveUser(String name, String username, String email) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String encryptedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setPassword(encryptedPassword);
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,accountRepository.findUserRoleByName("USER")));
        user.setRoles(userRoles);
        accountRepository.save(user);
        byte[] bytes;

        try {
            bytes = Files.readAllBytes(Constants.TEMP_FOLDER.toPath());
            String fileName = user.getId() + ".png";
            Path path = Paths.get(Constants.USER_FOLDER+fileName);
            Files.write(path,bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mailSender.send(emailConstructor.constructNewUserEmail(user,password));
        return user;
    }

    @Override
    public void updateUserPassword(User appUser, String newPassword) {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        appUser.setPassword(encryptedPassword);
        accountRepository.save(appUser);
        mailSender.send(emailConstructor.constructResetPasswordEmail(appUser,newPassword));
    }

    @Override
    public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
        byte[] bytes;

        try {
            Files.deleteIfExists(Paths.get(Constants.USER_FOLDER+"/"+userImageId+".png"));
            bytes = multipartFile.getBytes();
            Path path = Paths.get(Constants.USER_FOLDER+userImageId+".png");
            Files.write(path,bytes);
            return "User picture saved to server";
        }catch (Exception e){
            return "User picture saved";
        }
    }
}
















