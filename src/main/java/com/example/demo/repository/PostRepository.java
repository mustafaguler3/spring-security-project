package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p order by p.postedDate DESC")
    List<Post> findAll();
    @Query("select p from Post p where p.username =: username order by p.postedDate DESC")
    List<Post> findByUsername(@Param("username") String username);
    @Query("select p from Post p where p.id =: x")
    Post findPostById(@Param("x") Long id);
    @Query("delete from Post p where p.id =:x")
    void deletePostById(@Param("x") Long id);
}

















