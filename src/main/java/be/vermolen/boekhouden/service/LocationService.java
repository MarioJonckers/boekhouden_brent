package be.vermolen.boekhouden.service;

import be.vermolen.boekhouden.exception.CityNotFoundException;
import be.vermolen.boekhouden.exception.CountryNotFoundException;
import be.vermolen.boekhouden.exception.CreateException;
import be.vermolen.boekhouden.model.City;
import be.vermolen.boekhouden.model.Country;
import be.vermolen.boekhouden.repository.CityRepository;
import be.vermolen.boekhouden.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static be.vermolen.boekhouden.util.MyStringUtil.trim;

@Service
@AllArgsConstructor
public class LocationService {

    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public City getById(Long id) {
        City city = cityRepository.findById(id).orElse(null);
        if (city == null) {
            throw new CityNotFoundException(id);
        }
        return city;
    }

    public List<City> getByPostalCode(String postalCode) {
        List<City> cities = cityRepository.findAllByPostalCode(postalCode);
        if (cities.size() == 0) {
            throw new CityNotFoundException(postalCode, true);
        }
        return cities;
    }

    public List<City> getByCity(String city) {
        List<City> cities = cityRepository.findAllByCity(city);
        if (cities.size() == 0) {
            throw new CityNotFoundException(city);
        }
        return cities;
    }

    public Country getById(String id) {
        Country country = countryRepository.findById(id).orElse(null);
        if (country == null) {
            throw new CountryNotFoundException(id);
        }
        return country;
    }

    public Country getByName(String name) {
        Country country = countryRepository.findByName(name).orElse(null);
        if (country == null) {
            throw new CountryNotFoundException(name, true);
        }
        return country;
    }

    public City createCity(City city) {
        City newCity = new City();

        Country country = city.getCountry();
        if (country.getId() == null) {
            country = createCountry(country.getName());
        }

        try {
            List<City> citiesByPostalCode = getByPostalCode(city.getPostalCode());
            Optional<City> first = citiesByPostalCode.stream()
                    .filter(c -> c.getCity().equals(city.getCity()))
                    .findFirst();
            if (first.isPresent()) {
                newCity = first.get();
            }
        } catch (CityNotFoundException ignore) {
            if (city.getPostalCode() == null || city.getPostalCode().isBlank() ||
            city.getCity() == null || city.getCity().isBlank()) {
                throw new CreateException("stad", "Naam of postcode is leeg.");
            }
            newCity.setPostalCode(trim(city.getPostalCode(), false));
            newCity.setCity(trim(city.getCity(), true));
            newCity.setCountry(country);
            newCity = cityRepository.save(newCity);
        }

        return newCity;
    }

    private Country createCountry(String name) {
        Country country = new Country();
        try {
            country = getByName(name);
        } catch (CountryNotFoundException e) {
            if (name == null || name.isBlank()) {
                throw new CreateException("land", "Naam is leeg.");
            } else {
                country.setName(name);
                country = countryRepository.save(country);
            }
        }

        return country;
    }

    public List<City> getCities(String countryId) {
        return cityRepository.findAllByCountry_Id(countryId);
    }

    public List<Country> getCountries() {
        return countryRepository.findAll();
    }
}
