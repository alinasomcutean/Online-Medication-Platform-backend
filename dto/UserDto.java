package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserDto {

    UUID id;
    String firstName;
    String lastName;
    LocalDate dateBirth;
    Integer gender;
    AccountDto accountDto;
    AddressDto addressDto;

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.dateBirth = user.getDateBirth();
        this.gender = user.getGender();
        this.accountDto = new AccountDto(user.getAccount());
        this.addressDto = new AddressDto(user.getAddress());
    }
}
