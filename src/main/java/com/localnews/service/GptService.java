package com.localnews.service;

import com.localnews.dto.response.openai.GptResponse;

import java.util.concurrent.CompletableFuture;

public interface GptService {

    CompletableFuture<GptResponse> asyncResponse(String content, String city);
    GptResponse response(String content, String city);
}
