package com.photosharing.app.feedservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostResponseDto(
        UUID id,
        UUID authId,
        String caption,
        Instant createdAt,
        List<MediaItemDto> mediaList
) {
}
