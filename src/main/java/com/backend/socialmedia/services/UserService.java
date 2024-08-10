package com.backend.socialmedia.services;

import org.springframework.stereotype.Service;

import com.backend.socialmedia.entities.Follow;
import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.repositories.CommentRepository;
import com.backend.socialmedia.repositories.FollowRepository;
import com.backend.socialmedia.repositories.LikeRepository;
import com.backend.socialmedia.repositories.PostRepository;
import com.backend.socialmedia.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private FollowRepository followRepository;
    private NotificationService notificationService;


    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUserById(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){  
            User foundUser = user.get();
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setImage(newUser.getImage());
            userRepository.save(foundUser);
            return foundUser;
        }else
            return null;
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Object> getUserActivityById(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty()) {
            return null;
        }
        List<Object> comments = commentRepository.findUserCommentsByPostId(postIds);
        List<Object> likes = likeRepository.findUserLikesByPostId(postIds);
        List<Object> results = new ArrayList<>();
        results.addAll(comments);
        results.addAll(likes);
        return results;
    }

    public User updateUserProfile(Long userId, String bio, String profilePictureUrl) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBio(bio);
            user.setProfilePictureUrl(profilePictureUrl);
            return userRepository.save(user);
        }
        return null;
    }

    public void followUser(Long followerId, Long followingId) {
    User follower = userRepository.findById(followerId).orElse(null);
    User following = userRepository.findById(followingId).orElse(null);
    
    if (follower != null && following != null) {
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
        notificationService.createNotification(followingId, "You have a new follower.");
    }
}

    public void unfollowUser(Long followerId, Long followingId) {
        followRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

}
