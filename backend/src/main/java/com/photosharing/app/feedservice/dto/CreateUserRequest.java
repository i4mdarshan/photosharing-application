package com.photosharing.app.feedservice.dto;

public record CreateUserRequest(
        String username,
        String bio,
        String profilePictureUrl
) {
}
