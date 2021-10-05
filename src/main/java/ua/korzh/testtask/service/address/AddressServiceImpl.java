package ua.korzh.testtask.service.address;

import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.korzh.testtask.cache.CoordinatePair;
import ua.korzh.testtask.exception.NoSuchLocationException;
import ua.korzh.testtask.model.Address;
import ua.korzh.testtask.repository.LocationRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private LoadingCache<CoordinatePair, Address> addressCache;

    @Override
    @Transactional
    public Address getAddressByCoordinates(Double lat, Double lon) {

        var addressOptional = locationRepository.getLocationByLatAndLon(lat, lon);
        if (addressOptional.isPresent()) {
            return addressOptional.get().getAddress();
        }
        throw new NoSuchLocationException();
    }

    @Override
    @Transactional
    public List<Address> getAllAddresses() {

        var locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> {
                    var address = location.getAddress();
                    var coordinatePair = new CoordinatePair(location.getLat(), location.getLon());
                    addressCache.put(coordinatePair, address);
                    return address;
                })
                .collect(Collectors.toList());
    }
}
