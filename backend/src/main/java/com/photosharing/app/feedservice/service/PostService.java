package com.photosharing.app.feedservice.service;

import com.photosharing.app.feedservice.dto.CreatePostRequest;
import com.photosharing.app.feedservice.entity.PostEntity;
import com.photosharing.app.feedservice.entity.PostMediaEntity;
import com.photosharing.app.feedservice.repository.PostRepository;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    //private final ApplicationEventPublisher eventPublisher;
    private final SqsTemplate sqsTemplate;

    @Value("${spring.aws.sqs.queue-name}")
    private String queueName;

    public PostService(PostRepository postRepository, SqsTemplate sqsTemplate){
        this.postRepository = postRepository;
        //this.eventPublisher = eventPublisher;
        this.sqsTemplate = sqsTemplate;

    }

    @Transactional
    public UUID createPost(CreatePostRequest request){

        Instant transactionTime = Instant.now();
        PostEntity post = new PostEntity();
        post.setAuthId(request.authorId());
        post.setCaption(request.caption());
        post.setCreatedAt(transactionTime);

        List<PostMediaEntity> mediaList = request.media().stream().map(dto -> {
            PostMediaEntity media = new PostMediaEntity();
            media.setPost(post);
            media.setMediaURL(dto.mediaUrl());
            media.setMediaType(dto.mediaType());
            media.setDisplayOrder(dto.displayOrder());
            media.setCreatedAt(transactionTime);
            return media;
        }).toList();

        post.setMediaList(mediaList);

        PostEntity savedPost = postRepository.save(post);

        //eventPublisher.publishEvent(new PostCreatedEvent(savedPost.getId(), savedPost.getAuthId()));
        sqsTemplate.send(queueName, new PostCreatedEvent(
                savedPost.getId(),
                savedPost.getAuthId(),
                transactionTime.toEpochMilli()
        ));

        return savedPost.getId();
    }

    public record PostCreatedEvent(UUID postId, UUID authorId, long timestamp){}
}
