package ro.tuc.ds2020.services;

import org.springframework.security.core.userdetails.UserDetails;
import ro.tuc.ds2020.dto.LoginDto;
import ro.tuc.ds2020.model.Account;
import ro.tuc.ds2020.security.UserDetailsImpl;

public interface AccountService {

    LoginDto findByUsernameAndPassword(String username, String password);

    Account createAccount(String username, String password);

    Account updateAccount(Account account, String username, String password);

    UserDetails loadUserByUsername(String username);
}