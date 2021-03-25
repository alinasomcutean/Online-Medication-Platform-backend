package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PatientDto {

    UUID id;
    UserDto userDto;
    List<MedicationPlanDto> medicationPlanDtoList = new ArrayList<>();
    MedicalRecordDto medicalRecordDto;

    public PatientDto(Patient patient) {
        this.id = patient.getId();
        this.userDto = new UserDto(patient.getUser());
        if(!patient.getMedicationPlans().isEmpty()) {
            patient.getMedicationPlans().forEach(p -> this.medicationPlanDtoList.add(new MedicationPlanDto(p)));
        }
        this.medicalRecordDto = new MedicalRecordDto(patient.getMedicalRecord());
    }
}
