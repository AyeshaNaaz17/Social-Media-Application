package com.backend.socialmedia.requests;

import lombok.Data;

@Data
public class UpdatePostRequest {

    private String title;
    private String text;
}

