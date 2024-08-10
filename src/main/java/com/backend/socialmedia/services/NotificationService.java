package com.backend.socialmedia.services;

import org.springframework.stereotype.Service;
import com.backend.socialmedia.entities.Notification;
import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.repositories.NotificationRepository;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private UserService userService;

    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    public void createNotification(Long userId, String message) {
        User user = userService.getUserById(userId);
        if (user != null) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setMessage(message);
            notification.setCreateDate(new Date());
            notificationRepository.save(notification);
        }
    }

    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserId(userId);
    }
}
