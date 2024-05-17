package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(columnDefinition = "text")
    private String caption;
    private String location;
    private int likes;
    @CreationTimestamp
    private Date postedDate;
    private Integer userImageId;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    //@JoinColumn(name = "comment_id")
    private List<Comment> comments;

    public Post() {
    }

    public Stream<Comment> getComments() {
        if (comments != null){
            return comments.stream().sorted(Comparator.comparing(Comment::getPostedDate));
        }
        return null;
    }

    @JsonIgnore
    public void setComments(Comment comment) {
        if (comment != null){
            this.comments.add(comment);
        }
    }
}
















