package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dto.CaregiverDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.services.CaregiverService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasAuthority('ROLE_CAREGIVER') or hasAuthority('ROLE_DOCTOR')")
@CrossOrigin
public class CaregiverController {

    private final CaregiverService caregiverService;

    @GetMapping("/caregiver/patients/{caregiverId}")
    public ResponseEntity<List<PatientDto>> findPatientsByCaregiverId(@PathVariable String caregiverId) {
        return ResponseEntity.ok(caregiverService.findAssociatedPatients(UUID.fromString(caregiverId)));
    }
}
