package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dto.AddMedicationPlanDto;
import ro.tuc.ds2020.dto.MedicationPlanDto;
import ro.tuc.ds2020.services.MedicationPlanService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RolesAllowed({"ROLE_DOCTOR", "ROLE_CAREGIVER"})
@CrossOrigin
public class MedicationPlanController {

    private final MedicationPlanService medicationPlanService;

    @GetMapping("/patient/medicationPlans/{patientId}")
    public ResponseEntity<List<MedicationPlanDto>> findMedicationPlansForPatient(@PathVariable UUID patientId) {
        return ResponseEntity.ok(medicationPlanService.findMedicationPlanByPatientId(patientId));
    }

    @PostMapping("/patient/medicationPlan/create/{patientId}")
    public ResponseEntity<MedicationPlanDto> createMedicationPlan(@PathVariable String patientId, @RequestBody AddMedicationPlanDto addMedicationPlanDto) {
        return ResponseEntity.ok(medicationPlanService.createMedicationPlan(UUID.fromString(patientId), addMedicationPlanDto));
    }
}
