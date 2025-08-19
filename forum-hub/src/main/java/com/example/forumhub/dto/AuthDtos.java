package com.example.forumhub.dto;

public record RegisterRequest(String name, String email, String password) {}
public record LoginRequest(String email, String password) {}
public record AuthResponse(String token) {}
