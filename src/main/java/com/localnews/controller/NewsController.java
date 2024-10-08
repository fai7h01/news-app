package com.localnews.controller;

import com.localnews.dto.CityDto;
import com.localnews.dto.NewsDto;
import com.localnews.service.CityService;
import com.localnews.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;
    private final CityService cityService;

    public NewsController(NewsService newsService, CityService cityService) {
        this.newsService = newsService;
        this.cityService = cityService;
    }

    @GetMapping
    public String getPage(Model model){
        model.addAttribute("news", new NewsDto());
        model.addAttribute("cities", cityService.getAllCities());
        return "home";
    }

    @GetMapping("/search")
    public String findNewsByCity(@RequestParam("city") CityDto city, @ModelAttribute("news") NewsDto newsDto, Model model){
        NewsDto news = newsService.getNewsByCity(city);
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("news", news);
        return "article";
    }
}
