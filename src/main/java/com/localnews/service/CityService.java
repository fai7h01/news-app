package com.localnews.service;

import com.localnews.dto.CityDto;

import javax.transaction.Transactional;
import java.util.List;

public interface CityService {

    CityDto save(CityDto city);
    CityDto findById(Long id);
    List<CityDto> getAllCities();

}
