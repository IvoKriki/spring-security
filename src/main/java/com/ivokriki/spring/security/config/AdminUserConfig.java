package com.ivokriki.spring.security.config;

import com.ivokriki.spring.security.entities.Role;
import com.ivokriki.spring.security.entities.User;
import com.ivokriki.spring.security.repository.RoleRepository;
import com.ivokriki.spring.security.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    public static final String ADMIN = "admin";
    public static final String PASSWORD = "123";
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
                           UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByName(ADMIN)
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found in the database"));

        var userAdmin = userRepository.findByUsername(ADMIN);
        userAdmin.ifPresentOrElse(
                user -> System.out.print("admin already exists"),
                () -> {
                    var user = new User();
                    user.setUsername(ADMIN);
                    user.setPassword(passwordEncoder.encode(PASSWORD));
                    user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
