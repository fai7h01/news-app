package com.localnews.service.impl;

import com.localnews.client.OpenAIClient;
import com.localnews.dto.request.openai.GptRequest;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.dto.response.openai.Message;
import com.localnews.service.CityService;
import com.localnews.service.GptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GptServiceImpl implements GptService {

    @Value("${openAPI.secret-key}")
    private static String apiKey;
    private final String authHeader = "Bearer " + apiKey;
    private final String model = "gpt-4o-mini";
    private final OpenAIClient openAIClient;
    private final CityService cityService;

    public GptServiceImpl(OpenAIClient openAIClient, CityService cityService) {
        this.openAIClient = openAIClient;
        this.cityService = cityService;
    }


    @Override
    public GptResponse response(String prompt) {
        //
        GptRequest request = new GptRequest();
        request.setModel(model);
        request.setMessages(List.of(
                new Message("system", "You are assistant that will find news article by given city,"),
                new Message("user", "I want to find article that is about city: " + prompt + " or contains word: " + prompt)
        ));
        request.setMax_completion_tokens(100);
        GptResponse response = openAIClient.getCompletions(authHeader, request);
        return response;
    }
}
/*
news - city - isLocal
 */