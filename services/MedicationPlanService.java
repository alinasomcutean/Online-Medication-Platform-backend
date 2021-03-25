package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.AddMedicationPlanDto;
import ro.tuc.ds2020.dto.MedicationPlanDto;
import ro.tuc.ds2020.model.MedicationPlan;

import java.util.List;
import java.util.UUID;

public interface MedicationPlanService {

    List<MedicationPlanDto> findMedicationPlanByPatientId(UUID patientId);

    void deleteMedicationPlan(UUID patientId, UUID medicationPlanId);

    //MedicationPlanDto createMedicationPlan(UUID patientId, MedicationPlanDto medicationPlanDto);
    MedicationPlanDto createMedicationPlan(UUID patientId, AddMedicationPlanDto addMedicationPlanDto);

    void saveMedicationPlan(MedicationPlan medicationPlan);

    List<MedicationPlan> findAllMedicationPlans();
}
