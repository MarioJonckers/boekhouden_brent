package be.vermolen.boekhouden.repository;

import be.vermolen.boekhouden.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByPostalCode(String postalCode);
    List<City> findAllByCity(String city);
    List<City> findAllByCountry_Id(String countryId);
}
