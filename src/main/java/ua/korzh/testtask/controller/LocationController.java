package ua.korzh.testtask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.korzh.testtask.model.Location;
import ua.korzh.testtask.service.location.LocationService;
import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/location")
    public List<Location> searchLocationsByAddress(@RequestParam("address") String address) {

        return locationService.searchLocations(address);
    }



}
