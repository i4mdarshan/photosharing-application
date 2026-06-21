package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.dto.CreatePostRequest;
import com.photosharing.app.feedservice.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Map<String, UUID>> createPost(@RequestBody CreatePostRequest request){
        UUID postId = postService.createPost(request);
        return ResponseEntity.ok(Map.of("postId", postId));
    }
}
