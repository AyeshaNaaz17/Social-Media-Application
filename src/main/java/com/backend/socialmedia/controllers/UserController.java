package com.backend.socialmedia.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.exceptions.UserNotFoundException;
import com.backend.socialmedia.requests.UpdateUserProfileRequest;
import com.backend.socialmedia.responses.UserResponse;
import com.backend.socialmedia.services.UserService;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService){  //constructor injection
        this.userService=userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId){ 
        User user = userService.getUserById(userId);
        if(user==null){
            throw new UserNotFoundException();
        }
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")   //update
    public User updateUserById(@PathVariable Long userId, @RequestBody User newUser){
        return userService.updateUserById(userId, newUser);
    }

    @PutMapping("/users/{userId}/profile")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long userId, @RequestBody UpdateUserProfileRequest request) {
        User updatedUser = userService.updateUserProfile(userId, request.getBio(), request.getProfilePictureUrl());
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }   
    }

    @PostMapping("/users/{followerId}/follow/{followingId}")
    public ResponseEntity<String> followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        userService.followUser(followerId, followingId);
        return ResponseEntity.ok("User followed successfully.");
    }

    @DeleteMapping("/users/{followerId}/unfollow/{followingId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        userService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok("User unfollowed successfully.");
    }



    @DeleteMapping("/{userId}") //delete
    public void deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivityById(@PathVariable Long userId){
        return userService.getUserActivityById(userId);
    }

    /*@ResponseBody*/
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFoundException(){ 

    }
}
