package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.GeneralInfoPatientDto;
import ro.tuc.ds2020.dto.MedicationPlanDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PatientService {

    PatientDto createPatient(String firstName, String lastName, LocalDate dateBirth, Integer gender, String username, String password,
                             String street, Integer streetNumber, String city, String country, String description);

    List<GeneralInfoPatientDto> findAllPatients();

    PatientDto deletePatient(UUID id);

    GeneralInfoPatientDto updatePatientInfo(UserFrontDto userFrontDto);

    Patient findPatientById(UUID id);

    void savePatient(Patient patient);

    List<MedicationPlanDto> getMedicationPlansForPatient(UUID patientId);

    PatientDto findPatient(UUID id);
}
