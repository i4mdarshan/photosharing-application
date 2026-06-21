package com.photosharing.app.feedservice.service;

import com.photosharing.app.feedservice.entity.PostEntity;
import com.photosharing.app.feedservice.repository.PostRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final StringRedisTemplate redisTemplate;
    private final PostRepository postRepository;

    public FeedService(StringRedisTemplate redisTemplate, PostRepository postRepository){
        this.redisTemplate = redisTemplate;
        this.postRepository = postRepository;
    }

    public List<PostEntity> getHomeFeed(UUID userId, long cursorTimeStamp, int limit){

        String inboxKey = "feed:inbox:" + userId;

        // Fetch post IDs from Redis sorted by timestamp (cursor pagination)
        Set<String> postIdStr = redisTemplate.opsForZSet()
                .reverseRangeByScore(inboxKey, 0 , cursorTimeStamp - 1, 0, limit);

        if(postIdStr == null || postIdStr.isEmpty()){
            return List.of();
        }

        List<UUID> postIds = postIdStr.stream().map(UUID::fromString).collect(Collectors.toList());

        List<PostEntity> posts = postRepository.findAllById(postIds);

        return posts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
