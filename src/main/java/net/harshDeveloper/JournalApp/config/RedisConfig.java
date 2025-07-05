package net.harshDeveloper.JournalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
    RedisTemplate template = new RedisTemplate<>();
    template.setConnectionFactory(factory);


        ///  1) Use String  for Keys

        StringRedisSerializer KeySerilalizer = new StringRedisSerializer();
         template.setKeySerializer(KeySerilalizer);
       //  template.setHashKeySerializer(KeySerilalizer);
        template.setValueSerializer(KeySerilalizer);


//         ///  2) Use JSON for values.
//
//        GenericJackson2JsonRedisSerializer jsonRedisSerialize = new GenericJackson2JsonRedisSerializer();
//        template.setValueSerializer(jsonRedisSerialize);
//        template.setHashValueSerializer(jsonRedisSerialize);
//
//        template.afterPropertiesSet();

        return template;

    }

}
