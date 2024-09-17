package com.localnews.dto.response.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    private int index;
    private Message message;
    private Object logprobs;
    private String finishReason;

}
