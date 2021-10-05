package ua.korzh.testtask.service.address;

import ua.korzh.testtask.model.Address;

import java.util.List;

public interface AddressService {

    Address getAddressByCoordinates(Double lat, Double lon);

    List<Address> getAllAddresses();
}
