package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserFrontDto {

    UUID id;
    String firstName;
    String lastName;
    LocalDate dateBirth;
    Integer gender;
    String address;
    String username;
    String password;
    String medicalRecord;
}
