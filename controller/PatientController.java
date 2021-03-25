package ro.tuc.ds2020.controller;

;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dto.GeneralInfoPatientDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.services.PatientService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_PATIENT')")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/patient/{id}")
    public ResponseEntity<PatientDto> findPatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.findPatient(id));
    }
}
