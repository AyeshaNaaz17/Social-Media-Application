package com.backend.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.socialmedia.entities.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUserId(Long userId);
}
