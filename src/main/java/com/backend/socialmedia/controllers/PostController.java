package com.backend.socialmedia.controllers;

import org.springframework.web.bind.annotation.*;

import com.backend.socialmedia.entities.Post;
import com.backend.socialmedia.requests.CreatePostRequest;
import com.backend.socialmedia.requests.UpdatePostRequest;
import com.backend.socialmedia.responses.PostResponse;
import com.backend.socialmedia.services.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping 
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId) {  
        return postService.getAllPosts(userId);
    }

    @PostMapping
    public Post createPost(@RequestBody CreatePostRequest newPostRequest) {
        return postService.createPost(newPostRequest);
    }


    @GetMapping("/{postId}") 
    public PostResponse getPostById(@PathVariable Long postId) {
        return postService.getPostByIdWithLikes(postId);
    }

    @PutMapping("/{postId}")
    public Post updatePostById(@PathVariable Long postId, @RequestBody UpdatePostRequest updatePostRequest){
        return postService.updatePostById(postId,updatePostRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId){
        postService.deletePostById(postId);
    }

}
