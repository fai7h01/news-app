package com.localnews.service.impl;

import com.localnews.dto.CityDto;
import com.localnews.entity.City;
import com.localnews.repository.CityRepository;
import com.localnews.service.CityService;
import com.localnews.util.MapperUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final MapperUtil mapperUtil;

    public CityServiceImpl(CityRepository cityRepository, MapperUtil mapperUtil) {
        this.cityRepository = cityRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public CityDto save(CityDto city) {
        return mapperUtil.convert(cityRepository.save(mapperUtil.convert(city, new City())), new CityDto());
    }

    @Override
    public CityDto findById(Long id) {
        City found = cityRepository.findById(id).orElseThrow(() -> new NoSuchElementException("City not found."));
        return mapperUtil.convert(found, new CityDto());
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.findAll(Sort.by("name")).stream()
                .map(city -> mapperUtil.convert(city, new CityDto())).collect(Collectors.toList());
    }
}
