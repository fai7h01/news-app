package com.localnews.service.impl;

import com.localnews.client.OpenAIClient;
import com.localnews.dto.request.openai.GptRequest;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.dto.response.openai.Message;
import com.localnews.exception.GptResponseException;
import com.localnews.service.GptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class GptServiceImpl implements GptService {

    @Value("${openAPI.secret-key}")
    private String apiKey;
    private final OpenAIClient openAIClient;

    public GptServiceImpl(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }

    @Override
    public GptResponse response(String content, String city) {
        try {
            GptRequest request = new GptRequest();
            String authHeader = "Bearer " + apiKey;
            String model = "gpt-4o-mini";
            request.setModel(model);
            request.setMessages(List.of(
                    new Message("system", "Your task is to determine if the provided news article is about the given city. If the news article is about the city or contains key information related to the city, respond with 'Yes'. Otherwise, respond with 'No'."),
                    new Message("user", "Here is the news article: \"" + content + "\". Is this article about or related to the city: \"" + city + "\"?")
            ));
            request.setMax_completion_tokens(100);
            GptResponse response = openAIClient.getCompletions(authHeader, request);
            log.info("GPT response: {}", response.getChoices()[0].getMessage().getContent());
            return openAIClient.getCompletions(authHeader, request);
        }catch (Exception e){
            log.error("Error occurred during returning response.");
        }
        throw new GptResponseException("Request failed! Try again.");
    }

    @Async
    @Override
    public CompletableFuture<GptResponse> asyncResponse(String content, String city) {
        return CompletableFuture.supplyAsync(() -> response(content, city));
    }
}
