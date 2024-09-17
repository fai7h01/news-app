package com.localnews.service;

import com.localnews.dto.response.newsapi.NewsResponse;
import com.localnews.entity.News;

public interface NewsService {

    News getNewsByCity(String city);

}
