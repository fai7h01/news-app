package com.localnews.controller;

import com.localnews.annotation.ExecutionTime;
import com.localnews.dto.NewsDto;
import com.localnews.dto.ResponseWrapper;
import com.localnews.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://fai7h01.github.io")
@RestController
@RequestMapping("/api/v1/news")
@Tag(name = "News Controller", description = "News API")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ExecutionTime
    @GetMapping("/search/{cityId}")
    @Operation(summary = "Get News By Chosen City Id (1 - 500)")
    public ResponseEntity<ResponseWrapper> getContentByCity(@PathVariable("cityId") Long cityId){
        NewsDto newsByCity = newsService.getNewsByCity(cityId);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(newsByCity).build());
    }

}
