package ua.korzh.testtask.service.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.korzh.testtask.exception.NoSuchLocationException;
import ua.korzh.testtask.model.Location;
import ua.korzh.testtask.repository.LocationRepository;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Value("${address.search.url}")
    private String lookupUrl;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private RestTemplate rt;

    @Override
    @Transactional
    public List<Location> searchLocations(final String address) {

        ResponseEntity<List<Location>> res = rt.exchange(String.format(lookupUrl, address), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Location>>() {});

        var locations = res.getBody();
        if (locations == null) {
            throw new NoSuchLocationException();
        }

        locations.forEach(location -> {
            if (locationNotStored(location.getPlaceId())) {
                locationRepository.saveAndFlush(location);
            }
        });
        return locations;
    }

    private boolean locationNotStored(Long locationId) {

        return locationRepository.getLocationByPlaceId(locationId).isEmpty();
    }
}
