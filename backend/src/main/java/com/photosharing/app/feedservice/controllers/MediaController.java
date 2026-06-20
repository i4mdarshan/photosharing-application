package com.photosharing.app.feedservice.controllers;

import com.photosharing.app.feedservice.services.MediaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload-url")
    public ResponseEntity<MediaService.UploadUrlDto> getUploadUrl (
            @RequestParam String contentType,
            @RequestParam String extension
    ) {
        MediaService.UploadUrlDto response = mediaService.generatePreSignedUrl(contentType, extension);
        return ResponseEntity.ok(response);
    }
}
