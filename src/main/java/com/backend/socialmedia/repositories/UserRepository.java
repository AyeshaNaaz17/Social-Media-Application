package com.backend.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.socialmedia.entities.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
