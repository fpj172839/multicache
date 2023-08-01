package com.github.perry.multicache.config;

import com.github.perry.multicache.support.CacheMessageListener;
import com.github.perry.multicache.support.RedisCaffeineCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

/**
 * @Author fanpeijin
 * @Date 2023年06月25日 15点17分
 **/
@Configuration
//@AutoConfigureAfter(RedisConfig.class)
@EnableConfigurationProperties(CacheRedisCaffeineProperties.class)
public class CacheRedisCaffeineAutoConfiguration {
    @Autowired
    private CacheRedisCaffeineProperties cacheRedisCaffeineProperties;
    @Autowired
    @Qualifier("myRedisTemplate")
    RedisTemplate myRedisTemplate;

    @Bean
    @DependsOn(value = "myRedisTemplate")
    public RedisCaffeineCacheManager redisCaffeineCacheManager() {
        return new RedisCaffeineCacheManager(cacheRedisCaffeineProperties, myRedisTemplate);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
                                                                       RedisCaffeineCacheManager redisCaffeineCacheManager) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(myRedisTemplate.getConnectionFactory());
        CacheMessageListener cacheMessageListener = new CacheMessageListener(myRedisTemplate, redisCaffeineCacheManager);
        redisMessageListenerContainer.addMessageListener(cacheMessageListener, new ChannelTopic(cacheRedisCaffeineProperties.getRedis().getTopic()));
        return redisMessageListenerContainer;
    }
}
