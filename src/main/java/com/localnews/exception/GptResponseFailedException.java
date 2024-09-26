package com.localnews.exception;

public class GptResponseFailedException extends RuntimeException{
    public GptResponseFailedException(String message){
        super(message);
    }
}
