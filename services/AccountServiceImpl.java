package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.LoginDto;
import ro.tuc.ds2020.model.*;
import ro.tuc.ds2020.repository.AccountRepo;
import ro.tuc.ds2020.repository.CaregiverRepo;
import ro.tuc.ds2020.repository.DoctorRepo;
import ro.tuc.ds2020.repository.PatientRepo;
import ro.tuc.ds2020.security.UserDetailsImpl;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final PatientRepo patientRepo;
    private final CaregiverRepo caregiverRepo;
    private final DoctorRepo doctorRepo;
    private final AccountRepo accountRepo;

    @Override
    //Check if the corresponding account exists
    public LoginDto findByUsernameAndPassword(String username, String password) {
        //Find the account with the given username and password
        Account account = accountRepo.findByUsernameAndPassword(username, password);
        if (account != null) {
            User user = account.getUser();

            //Get all the possible users and for each of them check what type of user is logging in
            List<Patient> patients = patientRepo.findAll();
            List<Caregiver> caregivers = caregiverRepo.findAll();
            List<Doctor> doctors = doctorRepo.findAll();

            for (Patient p : patients) {
                if (p.getUser().equals(user)) {
                    return new LoginDto(user, "patient");
                }
            }

            for (Caregiver c : caregivers) {
                if (c.getUser().equals(user)) {
                    return new LoginDto(user, "caregiver");
                }
            }

            for (Doctor d : doctors) {
                if (d.getUser().equals(user)) {
                    return new LoginDto(user, "doctor");
                }
            }
        }

        return null;
    }

    @Override
    //Create a new account
    public Account createAccount(String username, String password) {
        Account account = Account.builder().username(username).password(password).build();
        accountRepo.save(account);
        return account;
    }

    @Override
    //Update an existing account
    public Account updateAccount(Account account, String username, String password) {
        account.setUsername(username);
        account.setPassword(password);
        accountRepo.save(account);
        return account;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepo.findByUsername(username);
        UUID id = null;
        List<Caregiver> caregivers = caregiverRepo.findAll();
        List<Patient> patients = patientRepo.findAll();

        for (Caregiver c : caregivers) {
            if (c.getUser().getId().equals(account.getUser().getId())) {
                id = c.getId();
            }
        }

        for (Patient p : patients) {
            if (p.getUser().getId().equals(account.getUser().getId())) {
                id = p.getId();
            }
        }

        return UserDetailsImpl.build(account, id);
    }
}
