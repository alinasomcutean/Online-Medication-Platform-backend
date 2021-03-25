package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class MedicationCreateDto {
    UUID id;
    String name;
    List<SideEffectsDto> sideEffectsDtos = new ArrayList<>();
    String dosage;
}
