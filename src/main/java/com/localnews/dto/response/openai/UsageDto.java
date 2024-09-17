package com.localnews.dto.response.openai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usage {

    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

}
