package com.localnews.client;

import com.localnews.dto.request.openai.GptRequest;
import com.localnews.dto.response.openai.GptResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://api.openai.com/v1/chat", name = "openai-client")
public interface OpenAIClient {

    @PostMapping("/completions")
    GptResponse getCompletions(@RequestHeader("Authorization") String authHeader,
                               @RequestBody GptRequest request);

}
