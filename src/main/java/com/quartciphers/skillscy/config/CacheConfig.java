package com.quartciphers.skillscy.config;

import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.setName("YouTubeResponse");
        configuration.setMemoryStoreEvictionPolicy("LRU");
        configuration.setMaxEntriesLocalHeap(1000);
        configuration.setTimeToLiveSeconds(1800);

        net.sf.ehcache.config.Configuration ehCacheConfig = new net.sf.ehcache.config.Configuration();
        ehCacheConfig.addCache(configuration);

        return net.sf.ehcache.CacheManager.newInstance(ehCacheConfig);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
