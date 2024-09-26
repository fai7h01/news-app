package com.localnews.controller;

import com.localnews.dto.CityDto;
import com.localnews.dto.ResponseWrapper;
import com.localnews.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> getAllCities(){
        List<CityDto> allCities = cityService.getAllCities();
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(allCities).build());
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<ResponseWrapper> getCityById(@PathVariable("cityId") Long id){
        CityDto city = cityService.findById(id);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(city).build());
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> addCity(@RequestBody CityDto city){
        CityDto saved = cityService.save(city);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(saved).build());
    }
}
