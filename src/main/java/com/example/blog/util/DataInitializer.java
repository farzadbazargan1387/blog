package com.example.blog.util;

import com.example.blog.model.Role;
import com.example.blog.model.User;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()){
            roleRepository.save(new Role(null, "ROLE_ADMIN"));
        }
        if(roleRepository.findByName("ROLE_USER").isEmpty()){
            roleRepository.save(new Role(null, "ROLE_USER"));
        }

        if(userRepository.findByUsername("admin").isEmpty()){
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            Role userRole = roleRepository.findByName("ROLE_USER").get();
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setFullName("Site Admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // change in prod
            admin.setRoles(Set.of(adminRole, userRole));
            admin.setEnabled(true);
            userRepository.save(admin);
        }
    }
}
