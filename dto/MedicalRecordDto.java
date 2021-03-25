package ro.tuc.ds2020.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ro.tuc.ds2020.model.MedicalRecord;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class MedicalRecordDto {

    UUID id;
    String description;

    public MedicalRecordDto(MedicalRecord medicalRecord) {
        this.id = medicalRecord.getId();
        this.description = medicalRecord.getDescription();
    }
}
