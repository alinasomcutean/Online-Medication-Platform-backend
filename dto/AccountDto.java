package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Account;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AccountDto {
    //UUID id;
    String username;
    String password;

    public AccountDto(Account account) {
       // this.id = account.getId();
        this.username = account.getUsername();
        this.password = account.getPassword();
    }
}