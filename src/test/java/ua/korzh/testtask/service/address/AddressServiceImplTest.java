package ua.korzh.testtask.service.address;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.korzh.testtask.exception.NoSuchLocationException;
import ua.korzh.testtask.repository.LocationRepository;
import ua.korzh.testtask.service.location.LocationService;

@SpringBootTest
public class AddressServiceImplTest {

    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AddressService addressService;

    @BeforeEach
    public void clearDb() {

        var address = "bakery+in+berlin+wedding";
        locationRepository.deleteAll();
        locationService.searchLocations(address);
    }

    @Test
    public void getAllAddressesTest() {

         var addresses = addressService.getAllAddresses();

        Assertions.assertEquals(10, addresses.size());
    }

    @Test
    public void getAddressByCoordinatesTest() {

        var lat = 52.5460941d;
        var lon = 13.35918d;

        var address = addressService.getAddressByCoordinates(lat, lon);

        Assertions.assertNotNull(address);
    }

    @Test
    public void getAddressByInvalidCoordinates() {

        var lat = 0d;
        var lon = 0d;

        Assertions.assertThrows(NoSuchLocationException.class, () -> {
            addressService.getAddressByCoordinates(lat, lon);
        });
    }
}