package com.drawproject.dev.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private Map<String, Bucket> bucketMap = new ConcurrentHashMap<>();

    public Bucket getBucketByClientId(HttpServletRequest request) {
        return bucketMap.computeIfAbsent(getClientIpAddress(request), key -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.simple(10, Duration.ofMinutes(5));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    public String getClientIpAddress(HttpServletRequest request) {
        WebAuthenticationDetails authDetails = (WebAuthenticationDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails();

        return authDetails.getRemoteAddress();
    }

}
