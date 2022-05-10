package be.vermolen.boekhouden.controller;

import be.vermolen.boekhouden.model.City;
import be.vermolen.boekhouden.model.Country;
import be.vermolen.boekhouden.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/city/{countryId}")
    public List<City> getAllCities(@PathVariable String countryId) {
        return locationService.getCities(countryId);
    }

    @GetMapping("/country")
    public List<Country> getAllCountries() {
        return locationService.getCountries();
    }
}
