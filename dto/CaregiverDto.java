package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.Caregiver;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CaregiverDto {

    UUID id;
    UserDto userDto;
    List<PatientDto> patientDtos = new ArrayList<>();
    //List<MedicationDto> medicationDtos = new ArrayList<>();

    public CaregiverDto(Caregiver caregiver) {
        this.id = caregiver.getId();
        this.userDto = new UserDto(caregiver.getUser());
        if(!caregiver.getPatients().isEmpty()){
            caregiver.getPatients().forEach(p -> this.patientDtos.add(new PatientDto(p)));
        }
        //caregiver.getMedications().forEach(m -> this.medicationDtos.add(new MedicationDto(m)));
    }
}
