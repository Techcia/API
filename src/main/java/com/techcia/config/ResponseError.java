package com.techcia.config;

import lombok.Data;

@Data
public class ResponseError {
    private String message;
    public ResponseError(String message){
        this.message = message;
    }
}
