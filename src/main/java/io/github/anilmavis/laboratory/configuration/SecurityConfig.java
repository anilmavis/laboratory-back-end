package io.github.anilmavis.laboratory.configuration;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import io.github.anilmavis.laboratory.model.Role;
import io.github.anilmavis.laboratory.repository.RoleRepository;
import io.github.anilmavis.laboratory.repository.UserRepository;
import io.github.anilmavis.laboratory.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private UserDetailsService service;
    
    @Autowired
    public SecurityConfig(UserDetailsServiceImpl service) {
        this.service = service;
    }
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable().
            authorizeRequests((requests) -> requests.anyRequest()
                              .authenticated())
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    
    @Bean
    public CommandLineRunner securityRunner(UserRepository repository, RoleRepository roleRepository) throws Exception {
        return (args) -> {
            Role userRole = roleRepository.save(new Role("USER"));
            Role adminRole = roleRepository.save(new Role("ADMIN"));
            io.github.anilmavis.laboratory.model.User user = new io.github.anilmavis.laboratory.model.User("user", passwordEncoder().encode("user"), Set.of(userRole));
            io.github.anilmavis.laboratory.model.User admin = new io.github.anilmavis.laboratory.model.User("admin", passwordEncoder().encode("admin"), Set.of(adminRole));
            repository.saveAll(Arrays.asList(user, admin));
        };
    }
}
