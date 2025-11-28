package com.example.blog.service;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User save(User u) { return userRepository.save(u); }
    public Optional<User> findByUsername(String username){ return userRepository.findByUsername(username);}
}
