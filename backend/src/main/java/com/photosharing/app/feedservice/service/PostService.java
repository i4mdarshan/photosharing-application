package com.photosharing.app.feedservice.service;

import com.photosharing.app.feedservice.dto.CreatePostRequest;
import com.photosharing.app.feedservice.entity.PostEntity;
import com.photosharing.app.feedservice.entity.PostMediaEntity;
import com.photosharing.app.feedservice.repository.PostRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PostService(PostRepository postRepository, ApplicationEventPublisher eventPublisher){
        this.postRepository = postRepository;
        this.eventPublisher = eventPublisher;

    }

    @Transactional
    public UUID createPost(CreatePostRequest request){

        PostEntity post = new PostEntity();
        post.setAuthId(request.authorId());
        post.setCaption(request.caption());

        List<PostMediaEntity> mediaList = request.media().stream().map(dto -> {
            PostMediaEntity media = new PostMediaEntity();
            media.setPost(post);
            media.setMediaURL(dto.mediaUrl());
            media.setMediaType(dto.mediaType());
            media.setDisplayOrder(dto.displayOrder());

            return media;
        }).toList();

        post.setMediaList(mediaList);

        PostEntity savedPost = postRepository.save(post);

        eventPublisher.publishEvent(new PostCreatedEvent(savedPost.getId(), savedPost.getAuthId()));

        return savedPost.getId();
    }

    public record PostCreatedEvent(UUID postId, UUID authorId){}
}
