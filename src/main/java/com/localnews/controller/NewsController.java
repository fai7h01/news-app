package com.localnews.controller;

import com.localnews.dto.CityDto;
import com.localnews.dto.NewsDto;
import com.localnews.dto.ResponseWrapper;
import com.localnews.service.CityService;
import com.localnews.service.NewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news-app")
public class NewsController {

    private final NewsService newsService;
    private final CityService cityService;

    public NewsController(NewsService newsService, CityService cityService) {
        this.newsService = newsService;
        this.cityService = cityService;
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseWrapper> getAllCities(){
        List<CityDto> allCities = cityService.getAllCities();
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(allCities).build());
    }

    @GetMapping("/articleByCity")
    public ResponseEntity<ResponseWrapper> getContentByCity(@RequestParam("city") String city){
        NewsDto newsByCity = newsService.getNewsByCity(city);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(newsByCity).build());
    }

}
