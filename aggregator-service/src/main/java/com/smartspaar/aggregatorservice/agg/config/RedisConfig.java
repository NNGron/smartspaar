package com.smartspaar.aggregatorservice.agg.config;

import com.smartspaar.model.SearchResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis config.
 * It sets key and value serializer.
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, SearchResponse> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, SearchResponse> t = new RedisTemplate<>();
        t.setConnectionFactory(cf);
        t.setKeySerializer(new StringRedisSerializer());
        t.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return t;
    }
}