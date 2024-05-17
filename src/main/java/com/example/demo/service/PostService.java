package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface PostService {
    Post savePost(User user, Map<String,String> request,String postImageName);
    List<Post> postList();
    Post getPostById(Long id);
    List<Post> findPostByUsername(String username);
    Post deletePost(Post post);
    String savePostImage(MultipartFile multipartFile, String fileName);
}















