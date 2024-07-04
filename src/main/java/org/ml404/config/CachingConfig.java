package org.ml404.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@EnableCaching
@Profile("test")
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("answers"),
                new ConcurrentMapCache("consultations"),
                new ConcurrentMapCache("customers"),
                new ConcurrentMapCache("doctors"),
                new ConcurrentMapCache("prescriptions"),
                new ConcurrentMapCache("questions"),
                new ConcurrentMapCache("subscriptions")
                ));
        return cacheManager;
    }
}
