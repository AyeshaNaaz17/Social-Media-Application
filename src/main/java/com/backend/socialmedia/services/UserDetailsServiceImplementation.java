package com.backend.socialmedia.services;

import com.backend.socialmedia.entities.User;
import com.backend.socialmedia.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService { //UserDetails için service oluşturduk.

    private UserRepository userRepository;

    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return com.backend.socialmedia.security.JWTUserDetails.create(user);  //gelen user' i UserDetails olarak döner.
    }

    public UserDetails loadUserById(Long id) {  //lazım olursa diye yazdım.
        User user = userRepository.findById(id).get();
        return com.backend.socialmedia.security.JWTUserDetails.create(user);
    }

}
