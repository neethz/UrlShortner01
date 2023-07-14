package com.neethu.urlshortener01.service;

import com.neethu.urlshortener01.encoders.BaseEncoder;
import com.neethu.urlshortener01.models.Url;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private static int counter;

    static {
        counter=100000;
    }
    private RedisTemplate<String, Url> redisTemplate;

    public UrlShortenerService(RedisTemplate<String, Url> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean checkIfPresentInRedis(String id){
        return redisTemplate.opsForValue().get(id) != null;
    }

    public String generateTinyUrl(){
        return BaseEncoder.encode(counter++);
    }

}
