package net.haji.springmvcthymeleafsecured.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user3").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("user4").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(Customizer.withDefaults()) //
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN"))
                .exceptionHandling(exp ->
                        exp.accessDeniedPage("/notAuthorized"));
        return httpSecurity.build();
    }
}
