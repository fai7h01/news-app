package com.localnews.service.impl;

import com.localnews.dto.response.openai.GptResponse;
import com.localnews.entity.News;
import com.localnews.repository.NewsRepository;
import com.localnews.service.GptService;
import com.localnews.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final GptService gptService;

    public NewsServiceImpl(NewsRepository newsRepository, GptService gptService) {
        this.newsRepository = newsRepository;
        this.gptService = gptService;
    }

    @Override
    public News getNewsByCity(String city) {
        List<News> allNews = newsRepository.findAll();
        for (News news : allNews) {
            String content = news.getContent();
            GptResponse response = gptService.response(content, city);
            if (response.getChoices()[0].getMessage().getContent().contains("yes") || response.getChoices()[0].getMessage().getContent().contains("Yes")){
                news.setCity(city);
                return news;
            }
        }
        return allNews.stream().findAny().orElseThrow(() -> new NoSuchElementException("News can not be found."));
    }
}
