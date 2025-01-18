package com.hariSolution.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration  // Marks the class as a configuration class in Spring, containing bean definitions.
@EnableCaching  // Enables caching in the Spring application.
@Slf4j  // Adds a logger to the class for logging purposes.
public class RedisConfig {

    // Redis connection factory bean
    @Bean  // Marks this method as a bean definition for the Spring container
    public RedisConnectionFactory redisConnectionFactory() {
        log.info("Initializing Redis Connection Factory...");  // Logs the initialization of the Redis connection.
        return new LettuceConnectionFactory();  // Returns a new LettuceConnectionFactory, connecting to the default Redis instance (localhost:6379).
    }

    // RedisTemplate configuration with proper serializers
    @Bean  // Marks this method as a bean definition for the Spring container
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        log.info("Configuring RedisTemplate...");  // Logs the configuration of RedisTemplate.

        RedisTemplate<String, Object> template = new RedisTemplate<>();  // Creates a new RedisTemplate to interact with Redis.
        template.setConnectionFactory(connectionFactory);  // Sets the connection factory for the template.

        // Use StringRedisSerializer for keys (Serializes and deserializes keys as strings)
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use Jackson serializer for values (Serializes and deserializes values as JSON objects)
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;  // Returns the configured RedisTemplate.
    }

    // Redis CacheManager bean with custom cache configurations
    @Bean  // Marks this method as a bean definition for the Spring container
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        log.info("Initializing Redis Cache Manager with custom configurations...");  // Logs the initialization of the Redis Cache Manager.

        // Default cache configuration (TTL: 60 seconds)
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(60))  // Sets the Time-to-Live (TTL) of cache entries to 60 seconds.
                .disableCachingNullValues();  // Disables caching of null values.

        // Custom cache configurations
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();  // Creates a map to hold custom cache configurations.
        cacheConfigurations.put("shortLivedCache", defaultConfig.entryTtl(Duration.ofMinutes(1)));  // Cache with 1-minute TTL.
        cacheConfigurations.put("longLivedCache", defaultConfig.entryTtl(Duration.ofHours(1)));  // Cache with 1-hour TTL.

        // Builds and returns a RedisCacheManager with the default and custom cache configurations.
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)  // Sets the default cache configuration.
                .withInitialCacheConfigurations(cacheConfigurations)  // Adds the custom cache configurations.
                .build();  // Builds the CacheManager.
    }
}
