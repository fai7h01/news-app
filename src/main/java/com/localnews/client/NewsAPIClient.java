package com.localnews.client;

import com.localnews.dto.response.newsapi.NewsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://newsapi.org/v2", name = "news-api")
public interface NewsAPIClient {

    @GetMapping("/top-headlines")
    NewsResponse getNewsByCountry(@RequestParam(value = "country") String country , @RequestParam("apiKey") String apiKey);

    @GetMapping("/everything")
    NewsResponse getAllNewsByKeyword(@RequestParam("q") String query,
                                     @RequestParam("apiKey") String apiKey);

}
