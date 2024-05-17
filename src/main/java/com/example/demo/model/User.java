package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 5,message = "Password must be at least 5 characters long")
    private String password;
    @NotBlank(message = "Email must not be blank")
    @Email
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @CreatedDate
    private Date createdAt;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> likedPosts;


}















