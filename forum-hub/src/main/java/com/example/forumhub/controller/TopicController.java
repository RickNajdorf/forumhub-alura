package com.example.forumhub.controller;

import com.example.forumhub.dto.CreateTopicDto;
import com.example.forumhub.dto.TopicDto;
import com.example.forumhub.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicDto>> list() {
        return ResponseEntity.ok(topicService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(topicService.getById(id));
    }

    @PostMapping
    public ResponseEntity<TopicDto> create(@RequestBody @Valid CreateTopicDto dto, @AuthenticationPrincipal UserDetails user) {
        TopicDto created = topicService.create(dto, user.getUsername());
        return ResponseEntity.created(URI.create("/api/topics/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> update(@PathVariable Long id, @RequestBody @Valid CreateTopicDto dto) {
        return ResponseEntity.ok(topicService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        topicService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
