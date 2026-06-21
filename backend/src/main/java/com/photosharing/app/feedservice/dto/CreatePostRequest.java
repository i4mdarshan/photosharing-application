package com.photosharing.app.feedservice.dto;

import java.util.List;
import java.util.UUID;

public record CreatePostRequest(
        UUID authorId,
        String caption,
        List<MediaItemDto> media
) {
}
