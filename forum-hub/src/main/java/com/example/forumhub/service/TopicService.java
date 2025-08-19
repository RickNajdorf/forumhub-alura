package com.example.forumhub.service;

import com.example.forumhub.dto.CreateTopicDto;
import com.example.forumhub.dto.TopicDto;
import com.example.forumhub.exception.NotFoundException;
import com.example.forumhub.model.Topic;
import com.example.forumhub.model.User;
import com.example.forumhub.repository.CourseRepository;
import com.example.forumhub.repository.TopicRepository;
import com.example.forumhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public List<TopicDto> listAll() {
        return topicRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TopicDto getById(Long id) {
        return topicRepository.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Topic not found"));
    }

    @Transactional
    public TopicDto create(CreateTopicDto dto, String authorEmail) {
        var course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));
        User author = userRepository.findByEmail(authorEmail)
                .orElseThrow(() -> new NotFoundException("Author not found"));

        Topic t = Topic.builder()
                .title(dto.title())
                .message(dto.message())
                .course(course)
                .author(author)
                .build();
        topicRepository.save(t);
        return toDto(t);
    }

    @Transactional
    public TopicDto update(Long id, CreateTopicDto dto) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new NotFoundException("Topic not found"));
        topic.setTitle(dto.title());
        topic.setMessage(dto.message());
        var course = courseRepository.findById(dto.courseId()).orElseThrow(() -> new NotFoundException("Course not found"));
        topic.setCourse(course);
        topicRepository.save(topic);
        return toDto(topic);
    }

    @Transactional
    public void delete(Long id) {
        if (!topicRepository.existsById(id)) throw new NotFoundException("Topic not found");
        topicRepository.deleteById(id);
    }

    private TopicDto toDto(Topic t) {
        return new TopicDto(t.getId(), t.getTitle(), t.getMessage(), t.getCourse().getName(), t.getAuthor().getName());
    }
}
