package com.localnews.controller;

import com.localnews.dto.CityDto;
import com.localnews.dto.ResponseWrapper;
import com.localnews.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/city")
@Tag(name = "City Controller", description = "City CRUD Operations")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/list")
    @Operation(summary = "Get All US Cities")
    public ResponseEntity<ResponseWrapper> getAllCities(){
        List<CityDto> allCities = cityService.getAllCities();
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(allCities).build());
    }

    @GetMapping("/{cityId}")
    @Operation(summary = "Get City By Id (1 - 500)")
    public ResponseEntity<ResponseWrapper> getCityById(@PathVariable("cityId") Long id){
        CityDto city = cityService.findById(id);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(city).build());
    }

    @PostMapping
    @Operation(summary = "Add City")
    public ResponseEntity<ResponseWrapper> addCity(@RequestBody CityDto city){
        CityDto saved = cityService.save(city);
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .data(saved).build());
    }
}
