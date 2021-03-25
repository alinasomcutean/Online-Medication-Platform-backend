package ro.tuc.ds2020.services;

import ro.tuc.ds2020.model.Address;

public interface AddressService {

    Address createAddress(String street, Integer streetNumber, String city, String country);

    Address updateAddress(Address address, String[] newAddress);
}
