package com.photosharing.app.feedservice.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
public class FollowEntity {

    @Id
    @Column(name = "follower_id")
    private UUID followerId;

    @Id
    @Column(name = "followeeId")
    private UUID followeeId;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;

    public UUID getFollowerId() {
        return followerId;
    }

    public void setFollowerId(UUID followerId) {
        this.followerId = followerId;
    }

    public UUID getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(UUID followeeId) {
        this.followeeId = followeeId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
