package com.training.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
@RequiredArgsConstructor
public class InfoController {
    
    private final Environment env;
    private final DataSource dataSource;
    private final RedisTemplate<String, Object> redisTemplate;
    
    @GetMapping("/app")
    public Map<String, Object> getAppInfo() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("name", env.getProperty("APP_NAME", "Shop App"));
        info.put("dbHost", env.getProperty("DB_HOST", "localhost"));
        info.put("redisHost", env.getProperty("REDIS_HOST", "localhost"));
        return info;
    }
    
    @GetMapping("/health/details")
    public Map<String, Object> getHealthDetails() {
        Map<String, Object> health = new LinkedHashMap<>();
        
        // Application
        health.put("application", "UP");
        
        // PostgreSQL
        try (Connection conn = dataSource.getConnection()) {
            health.put("database", Map.of(
                "status", "UP",
                "product", conn.getMetaData().getDatabaseProductName()
            ));
        } catch (Exception e) {
            health.put("database", Map.of(
                "status", "DOWN",
                "error", e.getMessage()
            ));
        }
        
        // Redis
        try {
            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            health.put("redis", Map.of(
                "status", "UP",
                "response", pong
            ));
        } catch (Exception e) {
            health.put("redis", Map.of(
                "status", "DOWN",
                "error", e.getMessage()
            ));
        }
        
        return health;
    }
}
