package com.drawproject.dev.security;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtAuthEntryPoint authEntryPoint;
    private CustomUserDetailsService userDetailsService;
    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthEntryPoint authEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                 .requestMatchers(
                         "/swagger-resources/**",
                         "/configuration/security",
                         "/swagger-ui/**",
                         "/webjars/**"
                 ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                 .requestMatchers("/api/top-courses/**").permitAll()
                 .requestMatchers("/api/courses/search").permitAll()
                 .requestMatchers("/api/courses/create").authenticated()
                 .requestMatchers("/api/courses/update").authenticated()
                 .requestMatchers("/api/courses/{id}/feedback").permitAll()
                 .requestMatchers("/api/courses/{id}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers("/api/courses/{id}/student").hasAnyRole(DrawProjectConstaints.INSTRUCTOR,
                         DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF)
                 .requestMatchers("/api/courses/{id}/feedback").permitAll()
                 .requestMatchers("/api/users/{id}/courses").authenticated()
                 .requestMatchers("/api/courses/{courseId}/topic").permitAll()
                 .requestMatchers("/contact/saveMsg").permitAll()
                 .requestMatchers("/api/dashboard").authenticated()
                 .requestMatchers("/api/instructor/**").permitAll()
                 .requestMatchers("/api/profile/**").authenticated()
                 .requestMatchers("/api/post/showPosts").permitAll()
                 .requestMatchers("/api/post/showPostDetail").permitAll()
                 .requestMatchers("/api/post/showPostUser").authenticated()
                 .requestMatchers("/api/post/savePost").authenticated()
                 .requestMatchers("/api/post/closePost").authenticated()
                 .requestMatchers("/api/post/deletePost").hasRole("ADMIN")
                 .requestMatchers("/api/admin/getAllUser").hasRole("ADMIN")
                 .requestMatchers("/api/admin/disableUser").hasRole("ADMIN")
                 .requestMatchers("/contact/displayMessages").hasRole("ADMIN")
                 .requestMatchers("/contact/closeMsg").hasRole("ADMIN")
                .and()
                .httpBasic();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public  JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }
}
