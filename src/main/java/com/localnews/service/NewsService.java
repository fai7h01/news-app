package com.localnews.service;

import com.localnews.dto.CityDto;
import com.localnews.dto.NewsDto;

public interface NewsService {

    NewsDto getNewsByCity(CityDto cityDto);

}
