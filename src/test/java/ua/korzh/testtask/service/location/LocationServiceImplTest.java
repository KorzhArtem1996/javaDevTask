package ua.korzh.testtask.service.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.korzh.testtask.repository.LocationRepository;

@SpringBootTest
public class LocationServiceImplTest {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LocationService locationService;

    @BeforeEach
    public void clearDb() {

        locationRepository.deleteAll();
    }

    @Test
    public void searchLocationsTest() {

        var address = "bakery+in+berlin+wedding";

        var locations = locationService.searchLocations(address);

        Assertions.assertEquals(10, locations.size());
    }

    @Test
    public void searchLocationByIncorrectAddressTest() {

        var incorrectAddress = "";

        var locations = locationService.searchLocations(incorrectAddress);

        Assertions.assertEquals(0, locations.size());
    }
}