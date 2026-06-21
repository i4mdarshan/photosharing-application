package com.photosharing.app.feedservice.service;

import com.photosharing.app.feedservice.entity.UserEntity;
import com.photosharing.app.feedservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID createUser(String username, String bio, String profilePictureUrl){
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setBio(bio);
        user.setProfilePictureUrl(profilePictureUrl);
        return userRepository.save(user).getId();
    }
}
