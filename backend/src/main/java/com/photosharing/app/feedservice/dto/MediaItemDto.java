package com.photosharing.app.feedservice.dto;

public record MediaItemDto(
        String mediaUrl,
        String mediaType,
        Integer displayOrder
) {
}
