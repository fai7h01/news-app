package com.localnews.service;

import com.localnews.dto.request.openai.GptRequest;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.entity.News;

public interface GptService {

    GptResponse response(String content, String city);
    GptRequest searchNewsByCity(String city);

}
