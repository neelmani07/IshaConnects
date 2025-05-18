package com.sangha.common.interceptor;

import com.sangha.common.config.RateLimiterConfig;
import com.sangha.common.exception.TooManyRequestsException;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RateLimiterConfig rateLimiterConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String key = request.getRequestURI();
        Bucket bucket = rateLimiterConfig.resolveBucket(key);
        
        if (bucket.tryConsume(1)) {
            return true;
        }
        
        throw new TooManyRequestsException("Rate limit exceeded. Please try again later.");
    }
} 