package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.AddMedicationPlanDto;
import ro.tuc.ds2020.dto.MedicationDto;
import ro.tuc.ds2020.dto.MedicationPlanDto;
import ro.tuc.ds2020.model.Medication;
import ro.tuc.ds2020.model.MedicationPlan;
import ro.tuc.ds2020.model.Patient;
import ro.tuc.ds2020.repository.MedicationPlanRepo;
import ro.tuc.ds2020.repository.MedicationRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MedicationPlanServiceImpl implements MedicationPlanService {

    private final PatientService patientService;
    private final MedicationPlanRepo medicationPlanRepo;
    private final MedicationRepo medicationRepo;

    @Override
    //Get all the medication plans of a selected patient
    public List<MedicationPlanDto> findMedicationPlanByPatientId(UUID patientId) {
        //Find the selected patient
        Patient patient = patientService.findPatientById(patientId);

        //Transform the medication plans from entity to dto
        List<MedicationPlanDto> medicationPlanDtos = new ArrayList<>();
        patient.getMedicationPlans().forEach(m -> medicationPlanDtos.add(new MedicationPlanDto(m)));
        return medicationPlanDtos;
    }

    @Override
    //Delete a selected medication plan of a specific patient
    public void deleteMedicationPlan(UUID patientId, UUID medicationPlanId) {
        //Find the patient
        Patient patient = patientService.findPatientById(patientId);
        //Get his list with the medication plans
        List<MedicationPlan> medicationPlans = patient.getMedicationPlans();

        medicationPlans.removeIf(m -> m.getId().equals(medicationPlanId));

        medicationPlanRepo.deleteById(medicationPlanId);
    }

    @Override
    public MedicationPlanDto createMedicationPlan(UUID patientId, AddMedicationPlanDto addMedicationPlanDto) {
        //Find the patient for which you create a medication plan
        Patient patient = patientService.findPatientById(patientId);

        //Create an empty list of medications
        List<Medication> medications = new ArrayList<>();
        //Transform each medicationDto from frontend to an entity
        for (MedicationDto m : addMedicationPlanDto.getMedicationDtos()) {
            Medication med = medicationRepo.findById(m.getId()).orElseThrow(RuntimeException::new);
            medications.add(med);
        }

        //Create a new medication plan and save it
        MedicationPlan medicationPlan = MedicationPlan.builder().intakeIntervals(addMedicationPlanDto.getIntakeIntervals())
                .periodStart(LocalDate.parse(addMedicationPlanDto.getPeriodStart())).periodEnd(LocalDate.parse(addMedicationPlanDto.getPeriodEnd()))
                .medications(medications).build();
        medicationPlanRepo.save(medicationPlan);

        //Save the new medication plan in the patient list also
        patient.getMedicationPlans().add(medicationPlan);
        patientService.savePatient(patient);

        return new MedicationPlanDto(medicationPlan);
    }

    //Create a new medication plan
    /*public MedicationPlanDto createMedicationPlan(UUID patientId, MedicationPlanDto medicationPlanDto) {
        //Find the existing patient for which we want to add a new medication plan
        Patient patient = patientService.findPatientById(patientId);

        //Create a new medication plan and save it
        MedicationPlan medicationPlan = MedicationPlan.builder().intakeIntervals(medicationPlanDto.getIntakeIntervals())
                .periodStart(medicationPlanDto.getPeriodStart()).periodEnd(medicationPlanDto.getPeriodEnd())
                .medications(new ArrayList<>()).build();
        medicationPlanRepo.save(medicationPlan);

        //Save the medication plan in the patient list
        patient.getMedicationPlans().add(medicationPlan);
        patientService.savePatient(patient);
        return null;
    }*/

    @Override
    public void saveMedicationPlan(MedicationPlan medicationPlan) {
        medicationPlanRepo.save(medicationPlan);
    }

    @Override
    public List<MedicationPlan> findAllMedicationPlans() {
        return medicationPlanRepo.findAll();
    }
}
