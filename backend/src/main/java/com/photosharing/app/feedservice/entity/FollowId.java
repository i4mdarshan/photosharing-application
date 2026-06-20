package com.photosharing.app.feedservice.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class FollowId implements Serializable {

    private UUID followerId;
    private UUID followeeId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FollowId followId = (FollowId) o;
        return Objects.equals(followerId, followId.followerId) && Objects.equals(followeeId, followId.followeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(followerId, followeeId);
    }
}
