package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Patient;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class GeneralInfoPatientDto {

    UUID id;
    UserDto userDto;
    MedicalRecordDto medicalRecordDto;

    public GeneralInfoPatientDto(Patient patient) {
        this.id = patient.getId();
        this.userDto = new UserDto(patient.getUser());
        this.medicalRecordDto = new MedicalRecordDto(patient.getMedicalRecord());
    }
}
