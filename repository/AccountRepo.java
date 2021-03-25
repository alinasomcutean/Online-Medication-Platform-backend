package ro.tuc.ds2020.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.model.Account;

import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<Account, UUID> {

    Account findByUsernameAndPassword(String username, String password);

    Account findByUsername(String username);

    Boolean existsByUsername(String username);
}