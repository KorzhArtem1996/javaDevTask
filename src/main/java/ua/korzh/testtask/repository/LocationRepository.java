package ua.korzh.testtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.korzh.testtask.model.Location;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> getLocationByPlaceId(Long placeId);

    Optional<Location> getLocationByLatAndLon(Double lat, Double lon);
}
