package com.photosharing.app.feedservice.repository;

import com.photosharing.app.feedservice.entity.FollowEntity;
import com.photosharing.app.feedservice.entity.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, FollowId> {


}
