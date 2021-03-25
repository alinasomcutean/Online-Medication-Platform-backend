package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.MedicationDto;

import java.util.List;
import java.util.UUID;

public interface MedicationService {

    MedicationDto updateMedication(MedicationDto medicationDto);

    List<MedicationDto> findMedicationByPatientIdAndMedicationPlanId(UUID patientId, UUID medicationPlanId);

    MedicationDto createMedicationInMedPlan(UUID patientId, UUID medicalPlanId, MedicationDto medicationDto);

    MedicationDto createMedication(MedicationDto medicationDto);

    void deleteMedication(UUID id);

    List<MedicationDto> findAllMedications();

}
