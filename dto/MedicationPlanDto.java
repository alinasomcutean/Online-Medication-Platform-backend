package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.MedicationPlan;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class MedicationPlanDto {

    UUID id;
    Integer intakeIntervals;
    LocalDate periodStart;
    LocalDate periodEnd;
    List<MedicationDto> medicationDtos = new ArrayList<>();

    public MedicationPlanDto(MedicationPlan medicationPlan) {
        this.id = medicationPlan.getId();
        this.intakeIntervals = medicationPlan.getIntakeIntervals();
        this.periodStart = medicationPlan.getPeriodStart();
        this.periodEnd = medicationPlan.getPeriodEnd();
        medicationPlan.getMedications().forEach(m -> this.medicationDtos.add(new MedicationDto(m)));
    }
}
