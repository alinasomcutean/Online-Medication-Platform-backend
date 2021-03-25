package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.model.MedicalRecord;
import ro.tuc.ds2020.repository.MedicalRecordRepo;

@RequiredArgsConstructor
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService{

    private final MedicalRecordRepo medicalRecordRepo;

    @Override
    //Update an existing medical record of a patient
    //This can be done by the doctor
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, String newDescription) {
        medicalRecord.setDescription(newDescription);
        medicalRecordRepo.save(medicalRecord);
        return medicalRecord;
    }

    @Override
    //Create an empty medical record for a new patient
    public MedicalRecord createMedicalRecord(String description) {
        MedicalRecord medicalRecord = MedicalRecord.builder().description(description).build();
        medicalRecordRepo.save(medicalRecord);
        return medicalRecord;
    }
}
