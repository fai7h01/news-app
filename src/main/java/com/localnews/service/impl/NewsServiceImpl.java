package com.localnews.service.impl;

import com.localnews.dto.CityDto;
import com.localnews.dto.NewsDto;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.entity.News;
import com.localnews.repository.NewsRepository;
import com.localnews.service.CityService;
import com.localnews.service.GptService;
import com.localnews.service.NewsService;
import com.localnews.util.MapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.internal.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final GptService gptService;
    private final MapperUtil mapperUtil;
    private final CityService cityService;

    public NewsServiceImpl(NewsRepository newsRepository, GptService gptService, MapperUtil mapperUtil, CityService cityService) {
        this.newsRepository = newsRepository;
        this.gptService = gptService;
        this.mapperUtil = mapperUtil;
        this.cityService = cityService;
    }

    @Override
    public NewsDto getNewsByCity(Long cityId) {

        CityDto city = cityService.findById(cityId);
        List<News> allNews = newsRepository.findAll();
//
//        List<CompletableFuture<GptResponse>> futures = allNews.stream()
//                .map(news -> gptService.asyncResponse(news.getContent(), city.getName()))
//                .collect(Collectors.toList());
//
//        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
//
//        for (int i = 0; i < allNews.size(); i++) {
//            News news = allNews.get(i);
//            GptResponse response = futures.get(i).join();
//            if (response.getChoices()[0].getMessage().getContent().contains("Yes")){
//                NewsDto newsDto = mapperUtil.convert(news, new NewsDto());
//                newsDto.setCity(city);
//                newsDto.setLocal(true);
//                log.info("Found news: {}", newsDto.getContent());
//                return newsDto;
//            }
//        }

        List<Pair<News, CompletableFuture<GptResponse>>> futurePairs = allNews.stream()
                .map(news -> Pair.of(news, gptService.asyncResponse(news.getContent(), city.getName())))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futurePairs.stream()
                .map(Pair::getRight)
                .toArray(CompletableFuture[]::new)).join();

        for (Pair<News, CompletableFuture<GptResponse>> pair : futurePairs) {
            News news = pair.getLeft();
            GptResponse response = pair.getRight().join();

            if (response.getChoices()[0].getMessage().getContent().contains("Yes")) {
                NewsDto newsDto = mapperUtil.convert(news, new NewsDto());
                newsDto.setCity(city);
                newsDto.setLocal(true);
                log.info("Found news: {}", newsDto.getContent());
               return newsDto;
            }
        }
        News random = allNews.get(new Random().nextInt(allNews.size()));
        return mapperUtil.convert(random, new NewsDto());
    }
}
