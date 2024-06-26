package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 8116721011712410403L;

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
    @JoinColumn(name = "likedPostUser_id")
    private List<Post> likedPosts;


    public void setLikedPost(Post likedPosts) {
        this.likedPosts.add(likedPosts);
    }

    public void setPosts(Post post) {
        this.posts.add(post);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        for (UserRole role : roles){
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}















