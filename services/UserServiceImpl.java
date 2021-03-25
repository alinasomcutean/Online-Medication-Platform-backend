package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.Account;
import ro.tuc.ds2020.model.Address;
import ro.tuc.ds2020.model.User;
import ro.tuc.ds2020.repository.AccountRepo;
import ro.tuc.ds2020.repository.PatientRepo;
import ro.tuc.ds2020.repository.UserRepo;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final AccountService accountService;
    private final AddressService addressService;

    @Override
    //Create a new user
    public User createUser(String firstName, String lastName, LocalDate dateBirth, Integer gender, String username, String password,
                           String street, Integer streetNumber, String city, String country) {
        //Create an account and an address before
        Account account = accountService.createAccount(username, password);
        Address address = addressService.createAddress(street, streetNumber, city, country);

        //Create a user and save it
        User user = User.builder().firstName(firstName).lastName(lastName).dateBirth(dateBirth).gender(gender).account(account).address(address).build();
        userRepo.save(user);

        //Set the user to the account and save the modification
        account.setUser(user);
        accountRepo.save(account);
        return user;
    }

    @Override
    public User updateUser(User oldUser, UserFrontDto userFrontDto) {
        //Find the updated account and address
        Account account = accountService.updateAccount(oldUser.getAccount(), userFrontDto.getUsername(), userFrontDto.getPassword());
        Address address = addressService.updateAddress(oldUser.getAddress(), userFrontDto.getAddress().split(" "));

        //Update the rest of info for the user
        oldUser.setFirstName(userFrontDto.getFirstName());
        oldUser.setLastName(userFrontDto.getLastName());
        oldUser.setDateBirth(userFrontDto.getDateBirth());
        oldUser.setGender(userFrontDto.getGender());
        oldUser.setAccount(account);
        oldUser.setAddress(address);
        userRepo.save(oldUser);

        return oldUser;
    }
}
