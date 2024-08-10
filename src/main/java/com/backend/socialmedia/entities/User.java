package com.backend.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "user")
@Data
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    private int image;

    private String bio;

    private String profilePictureUrl;

    // public String getBio() {
    //     return bio;
    // }

    // public void setBio(String bio) {
    //     this.bio = bio;
    // }

    // public String getProfilePictureUrl() {
    //     return profilePictureUrl;
    // }

    // public void setProfilePictureUrl(String profilePictureUrl) {
    //     this.profilePictureUrl = profilePictureUrl;
    // }

}

