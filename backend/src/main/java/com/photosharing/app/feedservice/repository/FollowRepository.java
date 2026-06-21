package com.photosharing.app.feedservice.repository;

import com.photosharing.app.feedservice.entity.FollowEntity;
import com.photosharing.app.feedservice.entity.FollowId;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {


    // SELECT f.followerId from Follow f WHERE f.followeeId = :followeeId;
    @Query("SELECT f.followerId from FollowEntity f WHERE f.followeeId = :followeeId")
    List<UUID> findFollowerIdsByFolloweeId(@Param("followeeId") UUID followeeId);
}
