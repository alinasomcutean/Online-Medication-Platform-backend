package ro.tuc.ds2020.services;

import ro.tuc.ds2020.model.MedicalRecord;

public interface MedicalRecordService {

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, String newDescription);

    MedicalRecord createMedicalRecord(String description);
}
