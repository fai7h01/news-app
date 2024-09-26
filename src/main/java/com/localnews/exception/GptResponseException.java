package com.localnews.exception;

public class GptResponseException extends RuntimeException{
    public GptResponseException(String message){
        super(message);
    }
}
