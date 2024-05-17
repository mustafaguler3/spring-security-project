package com.example.demo.service.Impl;

import com.example.demo.contant.Constants;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(User user, Map<String, String> request, String postImageName) {
        String caption = request.get("caption");
        String location = request.get("location");
        Post post = new Post();
        post.setCaption(caption);
        post.setLocation(location);
        post.setName(user.getUsername());
        post.setPostedDate(new Date());
        post.setUserImageId(user.getId());

        user.setPosts(post);

        postRepository.save(post);

        return post;
    }

    @Override
    public List<Post> postList() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findPostById(id);
    }

    @Override
    public List<Post> findPostByUsername(String username) {
        return postRepository.findByUsername(username);
    }

    @Override
    public Post deletePost(Post post) {
        try {
            Files.deleteIfExists(Paths.get(Constants.POST_FOLDER+"/"+post.getName()+".png"));
            postRepository.deletePostById(Long.valueOf(post.getId()));

            return post;
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }

    @Override
    public String savePostImage(MultipartFile multipartFile, String fileName) {

        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(Constants.POST_FOLDER+fileName+".png");
            Files.write(path,bytes, StandardOpenOption.CREATE);

        } catch (IOException e) {
            return "Error occured . Photo not saved";
        }

        return "Photo saved successfully";
    }
}
