package com.localnews.dto.response.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GptResponse {

    private String id;
    private String object;
    private long created;
    private String model;
    private String systemFingerprint;
    private Choice[] choices;
    private Usage usage;
}
