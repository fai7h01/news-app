package com.localnews.controller;

import com.localnews.dto.NewsDto;
import com.localnews.dto.ResponseWrapper;
import com.localnews.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/search/{cityId}")
    public ResponseEntity<ResponseWrapper> getContentByCity(@PathVariable("cityId") Long cityId){
        NewsDto newsByCity = newsService.getNewsByCity(cityId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(newsByCity).build());
    }

}
