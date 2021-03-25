package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.model.Address;
import ro.tuc.ds2020.repository.AddressRepo;
import ro.tuc.ds2020.services.AddressService;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    @Override
    //Create a new address
    public Address createAddress(String street, Integer streetNumber, String city, String country) {
        Address address = Address.builder().street(street).streetNumber(streetNumber).city(city).country(country).build();
        addressRepo.save(address);
        return address;
    }

    @Override
    //Update an existing address
    public Address updateAddress(Address address, String[] newAddress) {
        address.setStreet(newAddress[0]);
        address.setStreetNumber(Integer.parseInt(newAddress[2].substring(0, 1)));
        address.setCity(newAddress[3]);
        address.setCountry(newAddress[4]);
        addressRepo.save(address);
        return address;
    }
}
