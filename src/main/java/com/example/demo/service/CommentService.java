package com.example.demo.service;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;

public interface CommentService {
    void saveComment(Post post,String username,String content);
}
