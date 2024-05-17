package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AccountController {

    private PasswordEncoder passwordEncoder;
    private AccountService accountService;

    @Autowired
    public AccountController(PasswordEncoder passwordEncoder, AccountService accountService) {
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
    }
    @GetMapping("/")
    public ResponseEntity<?> userList(){
        List<User> users = accountService.userList();

        if (users.isEmpty()){
            return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable("username") String username){
        User user = accountService.findByUsername(username);
        if (user == null){
            return new ResponseEntity<>("No user found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<?> getUserListByUsername(@PathVariable("username") String username){
        List<User> user = accountService.getUserListByUsername(username);
        if (user == null){
            return new ResponseEntity<>("No user found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> request){
        String username = request.get("username");
        if (accountService.findByUsername(username) != null){
            return new ResponseEntity<>("username exists",HttpStatus.CONFLICT);
        }

        String email = request.get("email");
        if (accountService.findByEmail(email) != null){
            return new ResponseEntity<>("email exists",HttpStatus.CONFLICT);
        }

        String name = request.get("name");

        try {
            User user = accountService.saveUser(name,username,email);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An error occured",HttpStatus.BAD_REQUEST);
        }
    }


}

















