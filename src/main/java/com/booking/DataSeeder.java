package com.booking;

import com.booking.users.User;
import com.booking.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DataSeeder {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            String password = bCryptPasswordEncoder.encode("Foobar123!");
            if (repository.findByUsername("seed-user-1").isEmpty()) {
                repository.save(new User("seed-user-1", password, "ADMIN"));
            }
            if (repository.findByUsername("seed-user-2").isEmpty()) {
                repository.save(new User("seed-user-2", password, "ADMIN"));
            }
        };
    }
}
