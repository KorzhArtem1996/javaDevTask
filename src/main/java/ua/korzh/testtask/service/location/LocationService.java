package ua.korzh.testtask.service.location;

import ua.korzh.testtask.model.Location;
import java.util.List;

public interface LocationService {

    List<Location> searchLocations(String address);

}
