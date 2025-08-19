package com.example.forumhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTopicDto(@NotBlank String title, @NotBlank String message, @NotNull Long courseId) {}
