package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.dto.CreateUserRequest;
import com.photosharing.app.feedservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createUser(@RequestBody CreateUserRequest request){
        UUID userId = userService.createUser(request.username(), request.bio(), request.profilePictureUrl());
        return ResponseEntity.ok(Map.of("userId", userId));
    }
}
