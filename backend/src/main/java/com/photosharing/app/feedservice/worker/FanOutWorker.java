package com.photosharing.app.feedservice.worker;

import com.photosharing.app.feedservice.repository.FollowRepository;
import com.photosharing.app.feedservice.service.PostService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FanOutWorker {

    private final FollowRepository followRepository;
    private final StringRedisTemplate redisTemplate;

    private static final int CELEBRITY_THRESHOLD = 100000;

    public FanOutWorker(FollowRepository followRepository, StringRedisTemplate redisTemplate){
        this.followRepository = followRepository;
        this.redisTemplate = redisTemplate;
    }

    public void processPostCreatedEvent(PostService.PostCreatedEvent event){

        List<UUID> followers = followRepository.findFollowerIdsByFolloweeId(event.authorId());

        // Skip massive fan-out for celebrities
        if (followers.size() > CELEBRITY_THRESHOLD){
            // TODO: Write to celebrity outbox instead
            return;
        }

        followers.forEach(followerId -> {
            String inboxKey = "feed:inbox:" + followerId;
            redisTemplate.opsForZSet().add(inboxKey, event.postId().toString(), event.timestamp());

            // bound inbox size to keep storage predictable
            redisTemplate.opsForZSet().removeRange(inboxKey, 0, -501);
        });

    }
}
