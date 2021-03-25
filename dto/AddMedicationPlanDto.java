package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AddMedicationPlanDto {

    Integer intakeIntervals;
    String periodStart;
    String periodEnd;
    List<MedicationDto> medicationDtos = new ArrayList<>();
}
