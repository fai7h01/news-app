package com.localnews.service.impl;

import com.localnews.client.OpenAIClient;
import com.localnews.dto.request.openai.GptRequest;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.dto.response.openai.Message;
import com.localnews.service.GptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GptServiceImpl implements GptService {

    @Value("${openAPI.secret-key}")
    private String apiKey;
//    private final String authHeader = "Bearer " + apiKey;
//    private final String model = "gpt-4o-mini";
    private final OpenAIClient openAIClient;

    public GptServiceImpl(OpenAIClient openAIClient) {
        this.openAIClient = openAIClient;
    }


    @Override
    public GptRequest searchNewsByCity(String city) {
        GptRequest request = new GptRequest();
//        request.setModel(model);

        //list of articles

        return null;
    }

    @Override
    public GptResponse response(String content, String city) {
        try {
            GptRequest request = new GptRequest();
            String authHeader = "Bearer " + apiKey;
            String model = "gpt-4o-mini";
            request.setModel(model);
            request.setMessages(List.of(
                    new Message("system", "Use the provided city and news article and need to determine if this article is local to this city or contains that city." +
                            "If yes write: \"Yes\" or \"No\""),
                    new Message("user", "is this article: " + content + " is local to " + city + " or contains word " + city + "?"),
                    new Message("assistant", "Yes")
            ));
            request.setMax_completion_tokens(100);
            return openAIClient.getCompletions(authHeader, request);
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new RuntimeException("Can not return response.");
    }
}
/*
news - city - isLocal
 */