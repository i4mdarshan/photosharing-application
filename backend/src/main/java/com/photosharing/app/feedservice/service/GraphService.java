package com.photosharing.app.feedservice.service;

import com.photosharing.app.feedservice.entity.FollowEntity;
import com.photosharing.app.feedservice.repository.FollowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GraphService {


    private final FollowRepository followRepository;

    public GraphService(FollowRepository followRepository){
        this.followRepository = followRepository;
    }

    @Transactional
    public void followUser(UUID followerId, UUID followeeId){

        FollowEntity follow = new FollowEntity();
        follow.setFollowerId(followerId);
        follow.setFolloweeId(followeeId);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(UUID followerId, UUID followeeId){

        FollowEntity follow = new FollowEntity();
        follow.setFolloweeId(followeeId);
        follow.setFolloweeId(followeeId);
        followRepository.delete(follow);
    }


}
