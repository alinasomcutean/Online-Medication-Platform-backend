package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.User;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class LoginDto {
    UserDto userDto;
    String userType;

    public LoginDto(User user, String userType) {
        this.userDto = new UserDto(user);
        this.userType = userType;
    }
}
