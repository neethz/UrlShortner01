package com.neethu.urlshortener01.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Url {

    private String id;
    private String url;
    private LocalDateTime created;
}
