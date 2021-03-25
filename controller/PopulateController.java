package ro.tuc.ds2020.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.model.*;
import ro.tuc.ds2020.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ro.tuc.ds2020.model.ERole.*;

@RequiredArgsConstructor
@RestController
public class PopulateController {

    private final AccountRepo accountRepo;
    private final AddressRepo addressRepo;
    private final UserRepo userRepo;
    private final MedicalRecordRepo medicalRecordRepo;
    private final SideEffectRepo sideEffectRepo;
    private final MedicationRepo medicationRepo;
    private final MedicationPlanRepo medicationPlanRepo;
    private final PatientRepo patientRepo;
    private final CaregiverRepo caregiverRepo;
    private final DoctorRepo doctorRepo;

    @GetMapping("/populate")
    public void populateDb() {
        //Add users, accounts & addresses
        Account account1 = Account.builder().username("doctor").password(BCrypt.hashpw("doctor", BCrypt.gensalt())).role(ROLE_DOCTOR).build();
        Address address1 = Address.builder().street("Republicii").streetNumber(19).city("BM").country("RO").build();
        accountRepo.save(account1);
        addressRepo.save(address1);
        User user1 = User.builder().firstName("Alina").lastName("Somcutean").gender(1).
                dateBirth(LocalDate.of(1995, 9, 20)).account(account1).address(address1).build();
        userRepo.save(user1);

        Account account2 = Account.builder().username("patient").password(BCrypt.hashpw("patient", BCrypt.gensalt())).role(ROLE_PATIENT).build();
        Address address2 = Address.builder().street("Eroilor").streetNumber(34).city("CJ").country("RO").build();
        accountRepo.save(account2);
        addressRepo.save(address2);
        User user2 = User.builder().firstName("Luna").lastName("Bobi").gender(1).
                dateBirth(LocalDate.of(2015, 9, 20)).account(account2).address(address2).build();
        userRepo.save(user2);

        Account account3 = Account.builder().username("caregiver").password(BCrypt.hashpw("caregiver", BCrypt.gensalt())).role(ROLE_CAREGIVER).build();
        Address address3 = Address.builder().street("Herculane").streetNumber(9).city("CJ").country("RO").build();
        accountRepo.save(account3);
        addressRepo.save(address3);
        User user3 = User.builder().firstName("Rafi").lastName("Bobi").gender(1).
                dateBirth(LocalDate.of(2015, 9, 25)).account(account3).address(address3).build();
        userRepo.save(user3);

        //Add medical records
        MedicalRecord medicalRecord1 = MedicalRecord.builder().description("First medical record").build();
        medicalRecordRepo.save(medicalRecord1);

        MedicalRecord medicalRecord2 = MedicalRecord.builder().description("Second medical record").build();
        medicalRecordRepo.save(medicalRecord2);

        //Add side effects
        SideEffect sideEffect1 = SideEffect.builder().name("febra").build();
        SideEffect sideEffect2 = SideEffect.builder().name("tuse").build();
        SideEffect sideEffect3 = SideEffect.builder().name("somnolenta").build();
        SideEffect sideEffect4 = SideEffect.builder().name("greata").build();
        sideEffectRepo.save(sideEffect1);
        sideEffectRepo.save(sideEffect2);
        sideEffectRepo.save(sideEffect3);
        sideEffectRepo.save(sideEffect4);

        //Add medications
        List<SideEffect> list1 = new ArrayList<>();
        list1.add(sideEffect1);
        list1.add(sideEffect2);

        List<SideEffect> list2 = new ArrayList<>();
        list2.add(sideEffect3);
        list2.add(sideEffect4);

        Medication medication1 = Medication.builder().name("Medication1").sideEffects(list1).dosage(3.0d).build();
        Medication medication2 = Medication.builder().name("Medication2").sideEffects(list2).dosage(7.0d).build();
        Medication medication3 = Medication.builder().name("Medication3").sideEffects(list2).dosage(7.0d).build();
        List<Medication> medicationList1 = new ArrayList<>();
        List<Medication> medicationList2 = new ArrayList<>();
        medicationList1.add(medication1);
        medicationList1.add(medication2);
        medicationList2.add(medication3);
        medicationRepo.save(medication1);
        medicationRepo.save(medication2);
        medicationRepo.save(medication3);

        //Add medication plan
        MedicationPlan medicationPlan1 = MedicationPlan.builder().intakeIntervals(8).medications(medicationList1)
            .periodStart(LocalDate.of(2021, 01, 1)).periodEnd(LocalDate.of(2021, 01, 10)).build();
        MedicationPlan medicationPlan2 = MedicationPlan.builder().intakeIntervals(6).medications(medicationList2)
                .periodStart(LocalDate.of(2021, 01, 01)).periodEnd(LocalDate.of(2021, 01, 7)).build();
        List<MedicationPlan> medicationPlanList1 = new ArrayList<>();
        List<MedicationPlan> medicationPlanList2 = new ArrayList<>();
        medicationPlanList1.add(medicationPlan1);
        medicationPlanList1.add(medicationPlan2);
        medicationPlanList2.add(medicationPlan2);
        medicationPlanRepo.save(medicationPlan1);
        medicationPlanRepo.save(medicationPlan2);

        //Add patient
        Patient patient1 = Patient.builder().user(user2).medicalRecord(medicalRecord1).medicationPlans(medicationPlanList1).build();
        List<Patient> patientList1 = new ArrayList<>();
        patientList1.add(patient1);
        patientRepo.save(patient1);

        //Patient patient2 = Patient.builder().user(user2).medicalRecord(medicalRecord2).medicationPlans(medicationPlanList2).build();
        //patientRepo.save(patient2);

        //Add caregiver
        Caregiver caregiver1 = Caregiver.builder().user(user3).patients(patientList1).build();
        caregiverRepo.save(caregiver1);

        //patient1.setCaregiver(caregiver1);
        //patientRepo.save(patient1);

        //Add doctor
        Doctor doctor1 = Doctor.builder().user(user1).build();
        doctorRepo.save(doctor1);
    }
}
