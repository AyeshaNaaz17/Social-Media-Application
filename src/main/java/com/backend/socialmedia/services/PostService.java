package com.backend.socialmedia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.backend.socialmedia.entities.Post;
import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.repositories.PostRepository;
import com.backend.socialmedia.requests.CreatePostRequest;
import com.backend.socialmedia.requests.UpdatePostRequest;
import com.backend.socialmedia.responses.LikeResponse;
import com.backend.socialmedia.responses.PostResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    private LikeService likeService;


    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    public void setLikeService(@Lazy LikeService likeService) {
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {                       
        List<Post> postList;
        if (userId.isPresent()) {       
            postList = postRepository.findByUserId(userId.get());
        }else{
            postList = postRepository.findAll();  
        }
        return postList.stream().map(post -> {
            List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null),Optional.of(post.getId()));
            return new PostResponse(post,likes);}).collect(Collectors.toList()); 
    }
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
    public PostResponse getPostByIdWithLikes(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        List<LikeResponse> likes = likeService.getAllLikes(Optional.ofNullable(null),Optional.of(postId));
        return new PostResponse(post,likes);
    }

    public Post createPost(CreatePostRequest newPostRequest) {
        User user = userService.getUserById(newPostRequest.getUserId()); 
        if(user==null){
            return null;
        }
        Post post = new Post();
        post.setId(newPostRequest.getId());
        post.setText(newPostRequest.getText());
        post.setTitle(newPostRequest.getTitle());
        post.setUser(user);
        post.setCreateDate(new Date());
        return postRepository.save(post);
    }

    public Post updatePostById(Long postId, UpdatePostRequest updatePostRequest) {  
        Optional <Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post updatePost = post.get();
            updatePost.setText(updatePostRequest.getText());
            updatePost.setTitle(updatePostRequest.getTitle());
            postRepository.save(updatePost);
            return updatePost;
        }
        return null;
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}

