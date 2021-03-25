package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dto.*;
import ro.tuc.ds2020.services.CaregiverService;
import ro.tuc.ds2020.services.MedicationPlanService;
import ro.tuc.ds2020.services.MedicationService;
import ro.tuc.ds2020.services.PatientService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
@CrossOrigin
public class DoctorController {

    private final PatientService patientService;
    private final MedicationService medicationService;
    private final MedicationPlanService medicationPlanService;
    private final CaregiverService caregiverService;

    @PostMapping("/patient/create")
    public ResponseEntity<PatientDto> createPatient(@RequestBody UserFrontDto userFrontDto) {
        String[] address = userFrontDto.getAddress().split(" ");
        return ResponseEntity.ok(patientService.createPatient(userFrontDto.getFirstName(), userFrontDto.getLastName(), userFrontDto.getDateBirth(), userFrontDto.getGender(),
                userFrontDto.getUsername(), userFrontDto.getPassword(), address[0], Integer.parseInt(address[2]), address[3], address[4],
                userFrontDto.getMedicalRecord()));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<GeneralInfoPatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity<PatientDto> deletePatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.deletePatient(id));
    }

    @PutMapping("/patient/update")
    public ResponseEntity<GeneralInfoPatientDto> updatePatientInfo(@RequestBody UserFrontDto userFrontDto) {
        return ResponseEntity.ok(patientService.updatePatientInfo(userFrontDto));
    }

    @GetMapping("/caregivers")
    public ResponseEntity<List<CaregiverDto>> getAllCaregivers() {
        return ResponseEntity.ok(caregiverService.findCaregiversDtos());
    }

    @PostMapping("/caregiver/create")
    public ResponseEntity<CaregiverDto> createCaregiver(@RequestBody UserFrontDto userFrontDto) {
        return ResponseEntity.ok(caregiverService.createCaregiver(userFrontDto));
    }

    @PutMapping("/caregiver/update")
    public ResponseEntity<CaregiverDto> updateCaregiver(@RequestBody UserFrontDto userFrontDto) {
        return ResponseEntity.ok(caregiverService.updateCaregiver(userFrontDto));
    }

    @DeleteMapping("/caregiver/delete/{id}")
    public void deleteCaregiver(@PathVariable UUID id) {
        caregiverService.deleteCaregiver(id);
    }

    @PostMapping("/medication/create")
    public ResponseEntity<MedicationDto> createMedication(@RequestBody MedicationDto medicationDto) {
        return ResponseEntity.ok(medicationService.createMedication(medicationDto));
    }

    @PutMapping("medication/update")
    public ResponseEntity<MedicationDto> updateMedication(@RequestBody MedicationDto medicationDto) {
        return ResponseEntity.ok(medicationService.updateMedication(medicationDto));
    }

    @DeleteMapping("/medication/delete/{id}")
    public void deleteMedication(@PathVariable UUID id) {
        medicationService.deleteMedication(id);
    }

    @DeleteMapping("/patient/medicationPlan/delete/{patientId}/{medicationPlanId}")
    public void deleteMedicationPlan(@PathVariable UUID patientId, @PathVariable UUID medicationPlanId) {
        medicationPlanService.deleteMedicationPlan(patientId, medicationPlanId);
    }
}
