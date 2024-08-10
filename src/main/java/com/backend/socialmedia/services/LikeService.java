package com.backend.socialmedia.services;

import org.springframework.stereotype.Service;

import com.backend.socialmedia.entities.Like;
import com.backend.socialmedia.entities.Post;
import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.repositories.LikeRepository;
import com.backend.socialmedia.requests.CreateLikeRequest;
import com.backend.socialmedia.responses.LikeResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;
    private NotificationService notificationService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()) {
            list = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(postId.isPresent()) {
            list = likeRepository.findByPostId(postId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());  //Like' ları alıp LikeResponse' a mapledik.
    }

    public Like getLikeById(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public Like createLike(CreateLikeRequest CreateLikeRequest) {
        User user = userService.getUserById(CreateLikeRequest.getUserId());
        Post post = postService.getPostById(CreateLikeRequest.getPostId());
        if(user != null && post != null) {
            Like like = new Like();
            like.setId(CreateLikeRequest.getId());
            like.setPost(post);
            like.setUser(user);
            notificationService.createNotification(post.getUser().getId(), "Your post received a new like.");
            return likeRepository.save(like);
        }else
            return null;
    }

    public void deleteLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }

}
