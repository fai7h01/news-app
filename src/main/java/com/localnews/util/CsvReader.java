package com.localnews.util;

import com.localnews.dto.CityDto;
import com.localnews.service.CityService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CsvReader {

    private final CityService cityService;

    public CsvReader(CityService cityService) {
        this.cityService = cityService;
    }

    public void readCsv(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                CityDto city = new CityDto();
                city.setName(values[0]);
                city.setState(values[2]);
                city.setPopulation(values[8]);
                cityService.save(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
