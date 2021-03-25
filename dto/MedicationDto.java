package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Medication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class MedicationDto {

    UUID id;
    String name;
    List<SideEffectsDto> sideEffectsDtos = new ArrayList<>();
    Double dosage;

    public MedicationDto(Medication medication) {
        this.id = medication.getId();
        this.name = medication.getName();
        if(!medication.getSideEffects().isEmpty()) {
            medication.getSideEffects().forEach(s -> this.sideEffectsDtos.add(new SideEffectsDto(s)));
        }
        this.dosage = medication.getDosage();
    }
}
