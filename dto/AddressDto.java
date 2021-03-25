package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Address;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AddressDto {
    UUID id;
    String street;
    Integer streetNumber;
    String city;
    String country;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.city = address.getCity();
        this.country = address.getCountry();
    }
}
