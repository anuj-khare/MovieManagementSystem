package com.Personal.MovieManagementSystem.Config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory getRedisConnection(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("redis-17570.c100.us-east-1-4.ec2.cloud.redislabs.com",17570);
        redisStandaloneConfiguration.setPassword("77U678QqZdtCWijlUoQ2XIYAVHhXGc67");//userName ->default
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
    @Bean
    @Primary
    public RedisTemplate<String,Object> getRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate template = new RedisTemplate();
//        template.setConnectionFactory(getRedisConnection()); -> This will create 2 beans : one by spring during initialization
//        and another by Java because the method definition uses new operator to return LettuceConnectionFactory,So we opt for DI.
          template.setConnectionFactory(lettuceConnectionFactory);
          template.setKeySerializer(new StringRedisSerializer());
          template.setHashKeySerializer(new StringRedisSerializer());
          template.setHashValueSerializer(new StringRedisSerializer());
          return template;
    }
}

/*
We need 2 types of beans:
    i)Connection Bean : To create connection
    ii)Template Bean : To define a structure about how i access data from redis
 */