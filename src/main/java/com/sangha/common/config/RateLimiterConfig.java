package com.sangha.common.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimiterConfig {
    
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    
    @Bean
    public Map<String, Bucket> rateLimiters() {
        return buckets;
    }
    
    public Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, this::newBucket);
    }
    
    private Bucket newBucket(String key) {
        // Different rate limits for different endpoints
        if (key.startsWith("/api/auth")) {
            // Auth endpoints: 5 requests per minute
            return createBucket(5, Duration.ofMinutes(1));
        } else if (key.startsWith("/api/posts")) {
            // Post endpoints: 30 requests per minute
            return createBucket(30, Duration.ofMinutes(1));
        } else if (key.startsWith("/api/comments")) {
            // Comment endpoints: 20 requests per minute
            return createBucket(20, Duration.ofMinutes(1));
        } else {
            // Default: 10 requests per minute
            return createBucket(10, Duration.ofMinutes(1));
        }
    }
    
    private Bucket createBucket(int tokens, Duration duration) {
        Refill refill = Refill.intervally(tokens, duration);
        Bandwidth limit = Bandwidth.classic(tokens, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }
} 