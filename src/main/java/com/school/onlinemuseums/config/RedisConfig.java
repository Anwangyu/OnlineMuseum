package com.school.onlinemuseums.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis配置类，配置Key的序列化器
 */
@Configuration
public class RedisConfig {

    /**
     * 设置RedisTemplate规则
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//      实例
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
//      将键序列化为字符串，在Redis中存储和检索数据
        redisTemplate.setKeySerializer(new StringRedisSerializer());
//      哈希键被序列为字符串
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//      建立联系与redis服务
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        return redisTemplate;
    }

}
