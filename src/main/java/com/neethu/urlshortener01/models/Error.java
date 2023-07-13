package com.neethu.urlshortener01.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Error {
    private String entity;
    private String value;
    private String message;
}
