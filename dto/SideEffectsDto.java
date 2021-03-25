package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.SideEffect;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class SideEffectsDto {

    UUID id;
    String name;

    public SideEffectsDto(SideEffect sideEffect) {
        this.id = sideEffect.getId();
        this.name = sideEffect.getName();
    }
}
