package com.localnews.service;

import com.localnews.dto.response.openai.GptResponse;

public interface GptService {

    GptResponse response(String content, String city);
}
