package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class JwtResponse {
    UUID id;
    String token;
    String username;
    List<String> roles;
}
