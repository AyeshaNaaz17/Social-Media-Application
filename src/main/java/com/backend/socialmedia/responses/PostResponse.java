package com.backend.socialmedia.responses;

import lombok.Data;

import java.util.List;

import com.backend.socialmedia.entities.Post;

@Data
public class PostResponse {  

    private Long id;
    private Long userId;
    private String username;
    private String text;
    private String title;

    private List<LikeResponse> postLikes;
    public PostResponse(Post post, List<LikeResponse> likes){ //constructor mapping
        this.id=post.getId();
        this.userId=post.getUser().getId();
        this.username=post.getUser().getUsername();
        this.title=post.getTitle();
        this.text=post.getText();
        this.postLikes=likes;
    }
}
