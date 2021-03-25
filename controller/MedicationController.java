package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dto.MedicationDto;
import ro.tuc.ds2020.services.MedicationService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_DOCTOR') or hasRole('ROLE_CAREGIVER')")
@CrossOrigin
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping("/medications")
    public ResponseEntity<List<MedicationDto>> findAllMedications() {
        return ResponseEntity.ok(medicationService.findAllMedications());
    }

    @GetMapping("patient/medicationPlan/medications/{patientId}/{medicationPlanId}")
    public List<MedicationDto> findMedicationByPatientAndMedPlan(@PathVariable UUID patientId, @PathVariable UUID medicationPlanId) {
        return medicationService.findMedicationByPatientIdAndMedicationPlanId(patientId, medicationPlanId);
    }

    @PostMapping("/patient/medication/create/{patientId}/{medicationPlanId}")
    public MedicationDto createMedicationInMedPlan(@PathVariable UUID patientId, @PathVariable UUID medicationPlanId, @RequestBody MedicationDto medicationDto) {
        return medicationService.createMedicationInMedPlan(patientId, medicationPlanId, medicationDto);
    }
}
