package com.sean.schedule.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@EnableCaching
@Configuration
public class RedisConfig {

    @Value("${zero.redis.host:127.0.0.1}")
    private String redisHost;

    // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取
    // 如果赋值为-1，则表示不限制，如果pool已经分配了maxActive个jedis实例，则此时pool的状态就成exhausted了
    @Value("${zero.redis.max-active:500}")
    private int redisMaxActive;

    // 控制一个pool最多有多少个状态为idle的jedis实例
    @Value("${zero.redis.max-idle:100}")
    private int redisMaxIdle;

    // 表示当borrow一个jedis实例时，最大的等待时间
    @Value("${zero.redis.max-wait-millis:5000}")
    private int redisMaxWaitMillis;

    @Value("${zero.redis.connection-timeout:6000}")
    private int redisConnectionTimeout;

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * 生成并配置RedisConnectionFactory
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // 配置jedis连接池
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        // 在borrow一个jedis实例时，是否提前进行validate操作，如果为true，则得到的jedis实例均是可用的
        jedisPoolConfig.setTestOnBorrow(true);
        // 在return给pool时，是否提前进行validate操作
        jedisPoolConfig.setTestOnReturn(false);
        // 如果为true，表示有一个扫描线程对idle object进行扫描，如果validate失败，此object会被从pool中drop掉；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
        jedisPoolConfig.setTestWhileIdle(true);

        jedisPoolConfig.setMaxIdle(redisMaxIdle);
        jedisPoolConfig.setMaxTotal(redisMaxActive);
        jedisPoolConfig.setMaxWaitMillis(redisMaxWaitMillis);

        // 当pool满了，阻塞住，或者达到maxWait时抛出异常
        jedisPoolConfig.setBlockWhenExhausted(true);

        JedisConnectionFactory factory = new JedisConnectionFactory(jedisPoolConfig);
        factory.setPort(6379);
        factory.setUsePool(true);
        factory.setHostName(redisHost);
        factory.setTimeout(redisConnectionTimeout);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());

        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);

        redisTemplate.setExposeConnection(true);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}