package com.localnews.service;

import com.localnews.dto.NewsDto;

public interface NewsService {

    //NewsDto findById(Long id);
    NewsDto getNewsByCity(Long cityId);

}
