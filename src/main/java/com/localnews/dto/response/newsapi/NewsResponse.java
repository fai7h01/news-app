package com.localnews.dto.response.newsapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponse {

    private String status;
    private int totalResults;
    private List<Article> articles;

}
/*
author": "POLITICO",
"title": "‘Not helpful’ for Trump and others to talk about pets being eaten, Ohio’s governor says - POLITICO",
"description": null,
"url": "https://www.politico.com/news/2024/09/15/mike-dewine-haitians-immigrants-dogs-cats-00179217",
"urlToImage": null,
"publishedAt": "2024-09-15T14:01:41Z",
"content": null
 */