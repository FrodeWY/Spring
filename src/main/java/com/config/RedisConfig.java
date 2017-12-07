package com.config;

import com.redis_domain.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


/**
 * Created by mac on 2017/12/6.
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory=new JedisConnectionFactory();
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPassword("123456");
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(5);
        jedisPoolConfig.setMaxWaitMillis(10000);
        jedisPoolConfig.setMaxTotal(100);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }
    @Bean
    public RedisTemplate<String,Product> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Product> redisTemplate=new RedisTemplate<String, Product>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        /*key 使用string类型的序列化器，value使用jackson2的序列化器，可选*/
       /* redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Product>(Product.class));*/
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
    /*如果所使用的key，value都是字符串可以使用StringRedisTemplate*/
   /* @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate(redisConnectionFactory);
        return new StringRedisTemplate();
    }*/
}
