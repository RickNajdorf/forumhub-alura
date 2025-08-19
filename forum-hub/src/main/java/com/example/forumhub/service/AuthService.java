package com.example.forumhub.service;

import com.example.forumhub.model.User;
import com.example.forumhub.repository.UserRepository;
import com.example.forumhub.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public String register(String name, String email, String rawPassword) {
        if (userRepository.findByEmail(email).isPresent()) throw new RuntimeException("Email already used");
        User u = User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(rawPassword))
                .role("ROLE_USER")
                .build();
        userRepository.save(u);
        return tokenProvider.createToken(email);
    }

    public String login(String email, String rawPassword) {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) throw new RuntimeException("Invalid credentials");
        return tokenProvider.createToken(email);
    }
}
