package com.localnews.service;

import com.localnews.dto.NewsDto;

public interface NewsService {

    NewsDto getNewsByCity(String city);

}
