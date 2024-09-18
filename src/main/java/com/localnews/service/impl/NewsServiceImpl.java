package com.localnews.service.impl;

import com.localnews.dto.CityDto;
import com.localnews.dto.NewsDto;
import com.localnews.dto.response.openai.GptResponse;
import com.localnews.entity.News;
import com.localnews.repository.NewsRepository;
import com.localnews.service.GptService;
import com.localnews.service.NewsService;
import com.localnews.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final GptService gptService;
    private final MapperUtil mapperUtil;

    public NewsServiceImpl(NewsRepository newsRepository, GptService gptService, MapperUtil mapperUtil) {
        this.newsRepository = newsRepository;
        this.gptService = gptService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public NewsDto getNewsByCity(CityDto city) {
        List<News> allNews = newsRepository.findAll();
        for (News news : allNews) {
            String content = news.getContent();
            GptResponse response = gptService.response(content, city.getName());
            if (response.getChoices()[0].getMessage().getContent().contains("yes") || response.getChoices()[0].getMessage().getContent().contains("Yes")){
                NewsDto newsDto = mapperUtil.convert(news, new NewsDto());
                newsDto.setCity(city);
                newsDto.setLocal(true);
                return newsDto;
            }
        }
        Optional<News> news = Optional.of(allNews.stream().findAny().orElseThrow());
        NewsDto random = mapperUtil.convert(news, new NewsDto());
        random.setLocal(false);
        return random;
    }
}
