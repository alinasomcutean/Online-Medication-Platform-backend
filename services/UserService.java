package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.User;

import java.time.LocalDate;

public interface UserService {

    User createUser(String firstName, String lastName, LocalDate dateBirth, Integer gender, String username, String password,
                    String street, Integer streetNumber, String city, String country);

    User updateUser(User oldUser, UserFrontDto userFrontDtos);
}
