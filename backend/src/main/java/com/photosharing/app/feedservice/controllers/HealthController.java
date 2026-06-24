package com.photosharing.app.feedservice.controllers;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/health")
public class HealthController {

    private final JdbcTemplate jdbcTemplate;
    private final StringRedisTemplate redisTemplate;

    public HealthController(JdbcTemplate jdbcTemplate, StringRedisTemplate redisTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/database")
    public ResponseEntity<Map<String, Object>> testDatabaseConnection (){

        Map<String, Object> healthStatus = new LinkedHashMap<>();
        boolean isHealthy = true;

        try {
            jdbcTemplate.execute("SELECT 1");
            healthStatus.put("database", "CONNECTED");
            healthStatus.put("status", "UP");
        } catch (Exception e) {
            healthStatus.put("database", "FAILED: " + e.getMessage());
            isHealthy = false;
        }

        return isHealthy
                ? ResponseEntity.ok(healthStatus)
                : ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(healthStatus);

    }

    @GetMapping("/redis")
    public ResponseEntity<Map<String, Object>> testRedisConnection (){

        Map<String, Object> healthStatus = new LinkedHashMap<>();
        boolean isHealthy = true;

        try {
            String pingResponse = Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().ping();
            healthStatus.put("redis", "CONNECTED (" + pingResponse + ")");
            healthStatus.put("status", "UP");
        } catch (Exception e) {
            healthStatus.put("redis", "FAILED: " + e.getMessage());
            isHealthy = false;
        }

        return isHealthy
                ? ResponseEntity.ok(healthStatus)
                : ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(healthStatus);

    }


}
