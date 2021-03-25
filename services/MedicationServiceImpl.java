package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.MedicationDto;
import ro.tuc.ds2020.dto.SideEffectsDto;
import ro.tuc.ds2020.model.Medication;
import ro.tuc.ds2020.model.MedicationPlan;
import ro.tuc.ds2020.model.Patient;
import ro.tuc.ds2020.model.SideEffect;
import ro.tuc.ds2020.repository.MedicationRepo;
import ro.tuc.ds2020.repository.PatientRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MedicationServiceImpl implements MedicationService {

    private final SideEffectsService sideEffectsService;
    private final MedicationPlanService medicationPlanService;
    private final MedicationRepo medicationRepo;
    private final PatientRepo patientRepo;

    @Override
    //Update an existing medication
    public MedicationDto updateMedication(MedicationDto medicationDto) {
        Medication medication = medicationRepo.findById(medicationDto.getId()).orElseThrow(RuntimeException::new);

        List<SideEffect> sideEffects = new ArrayList<>();
        for (SideEffectsDto s : medicationDto.getSideEffectsDtos()) {
            SideEffect sideEffect = sideEffectsService.getByName(s.getName());
            sideEffects.add(sideEffect);
        }

        medication.setName(medicationDto.getName());
        medication.setDosage(medicationDto.getDosage());
        medication.setSideEffects(sideEffects);

        medicationRepo.save(medication);
        return new MedicationDto(medication);
    }

    @Override
    //Get all the medications from a medication plan, for a selected patient
    public List<MedicationDto> findMedicationByPatientIdAndMedicationPlanId(UUID patientId, UUID medicationPlanId) {
        //Get the selected patient
        Patient patient = patientRepo.findById(patientId).orElseThrow(RuntimeException::new);

        //Medication list which will be displayed
        List<MedicationDto> medicationDtos = new ArrayList<>();

        //Find the medication plan selected
        for (MedicationPlan m : patient.getMedicationPlans()) {
            if (m.getId().equals(medicationPlanId)) {
                //For that medication plan, transform from entity to dto in order to display them
                for (Medication medication : m.getMedications()) {
                    medicationDtos.add(new MedicationDto(medication));
                }
            }
        }

        return medicationDtos;
    }

    @Override
    //Create a new medication
    public MedicationDto createMedication(MedicationDto medicationDto) {
        //Create the side effects list of that medication
        List<SideEffect> sideEffects = new ArrayList<>();
        for (SideEffectsDto s : medicationDto.getSideEffectsDtos()) {
            SideEffect sideEffect = sideEffectsService.getByName(s.getName());
            sideEffects.add(sideEffect);
        }

        //Save the new medication
        Medication medication = Medication.builder().name(medicationDto.getName()).dosage(medicationDto.getDosage())
                .sideEffects(sideEffects).build();
        medicationRepo.save(medication);

        return new MedicationDto(medication);
    }

    @Override
    //Create a new medication for a selected patient and in a specific medication plan
    public MedicationDto createMedicationInMedPlan(UUID patientId, UUID medicalPlanId, MedicationDto medicationDto) {
        //Get the selected patient
        Patient patient = patientRepo.findById(patientId).orElseThrow(RuntimeException::new);

        //Create the list with the side effects of the new medication
        List<SideEffect> sideEffects = new ArrayList<>();
        for (SideEffectsDto s : medicationDto.getSideEffectsDtos()) {
            sideEffects.add(sideEffectsService.getByName(s.getName()));
        }

        //Create a new medication
        Medication medication = Medication.builder().name(medicationDto.getName()).dosage(medicationDto.getDosage())
                .sideEffects(sideEffects).build();
        medicationRepo.save(medication);

        //Find the medication plan in which we have to add a new medication
        for (MedicationPlan medicationPlan : patient.getMedicationPlans()) {
            if (medicationPlan.getId().equals(medicalPlanId)) {
                medicationPlan.getMedications().add(medication);
                medicationPlanService.saveMedicationPlan(medicationPlan);
            }
        }

        return new MedicationDto(medication);
    }

    @Override
    public void deleteMedication(UUID id) {
        //Find medication in db
        Medication medication = medicationRepo.findById(id).orElseThrow(RuntimeException::new);

        //Go through the medication plans and delete medication from there
        for (MedicationPlan m : medicationPlanService.findAllMedicationPlans()) {
            m.getMedications().removeIf(med -> med.getId().equals(id));
            medicationPlanService.saveMedicationPlan(m);
        }

        medicationRepo.delete(medication);
    }

    @Override
    public List<MedicationDto> findAllMedications() {
        List<Medication> medications = medicationRepo.findAll();
        List<MedicationDto> medicationDtos = new ArrayList<>();
        for (Medication m : medications) {
            medicationDtos.add(new MedicationDto(m));
        }
        return medicationDtos;
    }
}
