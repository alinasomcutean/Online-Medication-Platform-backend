package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.dto.SideEffectsDto;
import ro.tuc.ds2020.services.SideEffectsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_DOCTOR')")
@CrossOrigin
public class SideEffectsController {

    private final SideEffectsService sideEffectsService;

    @GetMapping("/sideEffects")
    public ResponseEntity<List<SideEffectsDto>> getSideEffects() {
        return ResponseEntity.ok(sideEffectsService.getSideEffects());
    }
}
