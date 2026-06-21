package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.service.GraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

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
