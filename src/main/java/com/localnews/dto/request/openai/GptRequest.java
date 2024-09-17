package com.localnews.dto.request.openai;

import com.localnews.dto.response.openai.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GptRequest {

    private String model;
    private List<Message> messages;
    private Integer max_completion_tokens;
    private boolean stream;

}
