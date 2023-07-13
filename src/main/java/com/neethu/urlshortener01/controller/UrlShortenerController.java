package com.neethu.urlshortener01.controller;

import com.google.common.hash.Hashing;
import com.neethu.urlshortener01.models.Error;
import com.neethu.urlshortener01.models.Url;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping(value = "/tiny")
public class UrlShortenerController {

    private RedisTemplate<String, Url> redisTemplate;

    @Value("${redis.ttl}")
    private long ttl;

    @Autowired
    public UrlShortenerController(RedisTemplate<String, Url> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostMapping
    public ResponseEntity createTinyUrl(@RequestBody @NonNull Url url){
        UrlValidator urlValidator= new UrlValidator(new String[]{"http", "https"});

        if(!urlValidator.isValid(url.getUrl())){
            Error error= new Error("url", url.getUrl(),"Invalid url");
            return ResponseEntity.badRequest().body(error);
        }

        String id= Hashing.murmur3_32_fixed().hashString(url.getUrl(), Charset.defaultCharset()).toString();
        url.setId(id);
        url.setCreated(LocalDateTime.now());

        redisTemplate.opsForValue().set(url.getId(),url,ttl, TimeUnit.SECONDS);



        return ResponseEntity.ok(url);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUrl(@PathVariable String id) throws URISyntaxException {
        Url url= redisTemplate.opsForValue().get(id);

        if(url==null){
            Error error= new Error("id",id,"No such key exists");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        URI uri= new URI(url.getUrl());
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setLocation(uri);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(httpHeaders).build();
    }


}
