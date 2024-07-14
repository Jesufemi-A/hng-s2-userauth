package com.hng_s2_userauth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfiguration {



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(c->
                c.requestMatchers("/auth/**").permitAll());

        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
