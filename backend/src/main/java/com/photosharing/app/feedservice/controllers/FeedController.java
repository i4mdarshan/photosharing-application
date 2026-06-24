package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.dto.PostResponseDto;
import com.photosharing.app.feedservice.entity.PostEntity;
import com.photosharing.app.feedservice.service.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService){
        this.feedService = feedService;
    }

    @GetMapping("/home")
    public ResponseEntity<List<PostResponseDto>> getHomeFeed(
            @RequestHeader("X-User-Id") UUID userId,
            // Default to Long.MAX_VALUE so the first request gets the absolute latest posts
            @RequestParam(defaultValue = "9223372036854775807") long cursor,
            @RequestParam(defaultValue = "10") int limit) {

        List<PostResponseDto> feed = feedService.getHomeFeed(userId, cursor, limit);
        return ResponseEntity.ok(feed);
    }
}
