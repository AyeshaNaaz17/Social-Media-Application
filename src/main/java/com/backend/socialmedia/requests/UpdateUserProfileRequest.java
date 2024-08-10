package com.backend.socialmedia.requests;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {

    private String bio;

    private String profilePictureUrl;
    
}
