package com.drawproject.dev.security;

import com.drawproject.dev.constrains.DrawProjectConstaints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                 .cors()
                 .and()
                .authorizeRequests()
                 .requestMatchers(
                         "/swagger-resources/**",
                         "/configuration/security",
                         "/swagger-ui/**",
                         "/webjars/**"
                 ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                 .requestMatchers("/api/v1/courses/top-courses/**").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/search").permitAll()
                 .requestMatchers(HttpMethod.POST, "/api/v1/courses").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/courses/{id}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/{id}").hasAnyRole(
                         DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF, DrawProjectConstaints.INSTRUCTOR
                 )
                 .requestMatchers(HttpMethod.GET, "/api/v1/viewcourses").hasAnyRole(
                         DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE
                 )
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/student").hasAnyRole(
                         DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.INSTRUCTOR
                 )
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/feedback").permitAll()
                 .requestMatchers(HttpMethod.PUT, "/api/v1/courses/{id}/feedback").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.POST, "/api/v1/courses/{id}/feedback").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/{id}/feedback/{feedbackId}").hasAnyRole(
                         DrawProjectConstaints.USER_ROLE, DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE
                 )
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/feedback/check").hasRole(
                         DrawProjectConstaints.USER_ROLE
                 )
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/topic").permitAll()
                 .requestMatchers(HttpMethod.POST, "/api/v1/courses/{id}/topic").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/courses/{id}/topic").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/courses/topic/{topicId}/close").hasAnyRole(DrawProjectConstaints.INSTRUCTOR,
                         DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF)
                 .requestMatchers(HttpMethod.POST, "/api/v1/lessons/").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/lessons/").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/lessons/").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/feature").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/courses/{id}/progress").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.GET, "/api/v1/users/{userId}/courses").hasAnyRole(
                         DrawProjectConstaints.USER_ROLE, DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF
                 )
                 .requestMatchers(HttpMethod.POST, "/api/v1/users/record").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/courses/{id}/report").hasAnyRole(
                         DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/courses/{id}/open").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.POST, "/api/v1/assignments").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/assignments/{id}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/assignments/{id}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/top-instructors").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/experiences").permitAll()
                 .requestMatchers(HttpMethod.PUT, "/api/v1/instructor/{userId}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/courses").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/certificates").permitAll()
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/all-courses").permitAll()
                 .requestMatchers(HttpMethod.POST, "/api/v1/instructor/certificates").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/instructor/certificates/{certificateId}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/instructor/certificates/{certificateId}").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/artworks").permitAll()
                 .requestMatchers(HttpMethod.POST, "/api/v1/instructor/artworks").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.DELETE, "/api/v1/instructor/artworks/{artworkId}").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.GET, "/api/v1/instructor/{userId}/submissions").hasAnyRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/instructor/submissions/{taskId}").hasAnyRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/users/studentwork").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.POST, "/api/v1/users/studentwork").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/users/studentwork").hasRole(DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.GET, "api/v1/users/id").hasAnyRole(DrawProjectConstaints.USER_ROLE, DrawProjectConstaints.INSTRUCTOR,
                                        DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF)
                 .requestMatchers(HttpMethod.GET, "/api/v1/users/posts").hasAnyRole(DrawProjectConstaints.INSTRUCTOR, DrawProjectConstaints.USER_ROLE)
                 .requestMatchers(HttpMethod.GET, "/api/v1/users/avatar").authenticated()
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/artworks/complete-check").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/certificates/complete-check").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.GET, "/api/v1/staff/artworks").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/artworks/accept").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/artworks/reject").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/certificates/accept").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.PUT, "/api/v1/staff/certificates/reject").hasAnyRole(DrawProjectConstaints.STAFF, DrawProjectConstaints.ADMIN_ROLE)
                 .requestMatchers(HttpMethod.GET, "api/v1/style").permitAll()
                 .requestMatchers("/api/v1/users/orders").authenticated()
                 .requestMatchers("/api/v1/dashboard").authenticated()
                 .requestMatchers("/api/v1/instructor/**").permitAll()
                 .requestMatchers("/api/v1/profile/**").authenticated()
                 .requestMatchers("/api/v1/cart/**").authenticated()
                 .requestMatchers("/api/v1/category/**").permitAll()
                 .requestMatchers("/api/v1/skill/**").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/comment/**").permitAll()
                 .requestMatchers(HttpMethod.POST,"/api/v1/comment/**").authenticated()
                 .requestMatchers(HttpMethod.DELETE,"/api/v1/comment/**").authenticated()
                 .requestMatchers(HttpMethod.PUT,"/api/v1/comment/**").authenticated()
                 .requestMatchers(HttpMethod.GET,"/api/v1/post/{postId}").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/post/search").permitAll()
                 .requestMatchers(HttpMethod.GET,"/api/v1/profile/posts").authenticated()
                 .requestMatchers(HttpMethod.GET,"/api/v1/post").permitAll()
                 .requestMatchers(HttpMethod.POST,"/api/v1/post").authenticated()
                 .requestMatchers(HttpMethod.PUT,"/api/v1/post/{id}").authenticated()
                 .requestMatchers("/api/v1/post/deletePost").hasAnyRole(DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF)
                 .requestMatchers("/api/v1/admin/**").hasAnyRole(DrawProjectConstaints.ADMIN_ROLE, DrawProjectConstaints.STAFF)
                 .requestMatchers(HttpMethod.POST,"/api/v1/contact").permitAll()
                 .requestMatchers(HttpMethod.POST,"/api/v1/pay").authenticated()
                 .requestMatchers(HttpMethod.GET,"/api/v1/pay/cancel").permitAll()
                 .requestMatchers("/api/v1/pay/success").authenticated()
                 .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/instructor").hasRole(DrawProjectConstaints.INSTRUCTOR)
                 .requestMatchers(HttpMethod.GET, "/api/v1/dashboard/instructor/income-month").hasRole(DrawProjectConstaints.INSTRUCTOR)
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
