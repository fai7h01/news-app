package com.localnews.converter;

import com.localnews.dto.CityDto;
import com.localnews.service.CityService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CityDtoConverter implements Converter<String, CityDto> {

    private final CityService cityService;

    public CityDtoConverter(@Lazy CityService cityService) {
        this.cityService = cityService;
    }

    @Override
    public CityDto convert(String source) {
        if (source == null || source.isEmpty()){
            return null;
        }
        return cityService.findById(Long.valueOf(source));
    }
}
