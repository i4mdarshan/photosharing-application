package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/users")
public class GraphController {

    private final GraphService graphService;

    public GraphController(GraphService graphService){
        this.graphService = graphService;
    }

    @PostMapping("/{followeeId}/follow")
    public ResponseEntity<Void> follow(
            @RequestHeader("X-User-Id") UUID followerId,
            @PathVariable UUID followeeId
    ){
        graphService.followUser(followerId, followeeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{followeeId}/follow")
    public ResponseEntity<Void> unfollow(
            @RequestHeader("X-User-Id") UUID followerId,
            @PathVariable UUID followeeId
    ){
        graphService.unfollowUser(followerId, followeeId);
        return ResponseEntity.ok().build();
    }

}
