package ua.korzh.testtask.controller;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.korzh.testtask.cache.CoordinatePair;
import ua.korzh.testtask.model.Address;
import ua.korzh.testtask.service.address.AddressService;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private LoadingCache<CoordinatePair, Address> addressCache;
    @Autowired
    private AddressService addressService;

    @GetMapping("/address/coordinate")
    public Address getAddressByCoordinates(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon) {

        var coordinates = new CoordinatePair(lat, lon);
        return addressCache.getUnchecked(coordinates);
    }

    @GetMapping(value = "/address")
    public List<Address> getAllAddresses() {

        return addressService.getAllAddresses();
    }
}
