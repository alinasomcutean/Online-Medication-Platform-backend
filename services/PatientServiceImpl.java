package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.GeneralInfoPatientDto;
import ro.tuc.ds2020.dto.MedicationPlanDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.*;
import ro.tuc.ds2020.repository.PatientRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final UserService userService;
    private final MedicalRecordService medicalRecordService;
    private final CaregiverService caregiverService;
    private final PatientRepo patientRepo;

    @Override
    //Create a new patient
    public PatientDto createPatient(String firstName, String lastName, LocalDate dateBirth, Integer gender, String username,
                                    String password, String street, Integer streetNumber, String city, String country, String description) {
        //Create a new user for it with an account and an address
        User user = userService.createUser(firstName, lastName, dateBirth, gender, username, password,
                street, streetNumber, city, country);

        //Create an empty medical record
        MedicalRecord medicalRecord = medicalRecordService.createMedicalRecord(description);

        //Create the patient with no medication plan
        Patient patient = Patient.builder().user(user).medicationPlans(new ArrayList<>()).medicalRecord(medicalRecord).build();
        patientRepo.save(patient);
        return new PatientDto(patient);
    }

    @Override
    //Find the general info for all patients
    public List<GeneralInfoPatientDto> findAllPatients() {
        List<GeneralInfoPatientDto> generalInfoPatientDtos = new ArrayList<>();
        List<Patient> patients = patientRepo.findAll();

        //Transform each patient from db into a dto
        for (Patient p : patients) {
            generalInfoPatientDtos.add(new GeneralInfoPatientDto(p));
        }

        return generalInfoPatientDtos;
    }

    @Override
    public PatientDto deletePatient(UUID id) {
        //Find the patient
        Patient patient = patientRepo.findById(id).orElseThrow(RuntimeException::new);

        //Find all the caregiver
        List<Caregiver> caregivers = caregiverService.findAllCaregivers();

        //Check for each caregiver if they have the patient that must be deleted
        for (Caregiver c : caregivers) {
            List<Patient> caregiverPatients = c.getPatients();
            //If the patient exist, first delete it from the caregiver
            caregiverPatients.removeIf(p -> p.getId().equals(id));
        }

        PatientDto patientDto = new PatientDto(patient);

        //Delete the patient from db
        patientRepo.deleteById(id);
        return patientDto;
    }

    @Override
    public GeneralInfoPatientDto updatePatientInfo(UserFrontDto userFrontDto) {
        //Find the existing patient in the db
        Patient patient = patientRepo.findById(userFrontDto.getId()).orElseThrow(RuntimeException::new);

        //Update its user info, including account and address
        patient.setUser(userService.updateUser(patient.getUser(), userFrontDto));

        //Update the description of the medical record
        patient.setMedicalRecord(medicalRecordService.updateMedicalRecord(patient.getMedicalRecord(), userFrontDto.getMedicalRecord()));

        //Save the updated patient
        patientRepo.save(patient);
        return new GeneralInfoPatientDto(patient);
    }

    @Override
    public Patient findPatientById(UUID id) {
        return patientRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public PatientDto findPatient(UUID id) {
        List<Patient> patientList = patientRepo.findAll();
        Patient patient = null;

        for (Patient p : patientList) {
            if (p.getId().equals(id)) {
                patient = p;
            }
        }

        return new PatientDto(patient);
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepo.save(patient);
    }

    @Override
    public List<MedicationPlanDto> getMedicationPlansForPatient(UUID patientId) {
        List<MedicationPlanDto> medicationPlanDtos = new ArrayList<>();
        Patient patient = patientRepo.findById(patientId).orElseThrow(RuntimeException::new);

        for (MedicationPlan m : patient.getMedicationPlans()) {
            medicationPlanDtos.add(new MedicationPlanDto(m));
        }

        return medicationPlanDtos;
    }
}
