package com.backend.socialmedia.responses;

import com.backend.socialmedia.entities.User;

import lombok.Data;

@Data
public class UserResponse {

    private Long id;
    private int imageId;
    private String username;

    public UserResponse(User user) {
        this.id = user.getId();
        this.imageId = user.getImage();
        this.username = user.getUsername();
    }
}