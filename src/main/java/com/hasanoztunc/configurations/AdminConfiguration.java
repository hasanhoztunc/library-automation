package com.hasanoztunc.configurations;

import com.hasanoztunc.configurations.properties.ApplicationProperties;
import com.hasanoztunc.enumerations.Role;
import com.hasanoztunc.repositories.UserRepository;
import com.hasanoztunc.security.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminConfiguration {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Bean
    public ApplicationRunner seedAdminUser(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ) {
        return args -> {
            String adminUsername = applicationProperties.getAdmin().getUsername();

            if (userRepository.findByUsername(adminUsername).isPresent()) {
                return;
            }

            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(bCryptPasswordEncoder.encode(applicationProperties.getAdmin().getPassword()));
            admin.getRoles().add(Role.ADMIN);

            userRepository.save(admin);
        };
    }
}
