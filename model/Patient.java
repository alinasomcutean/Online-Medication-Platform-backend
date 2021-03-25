package ro.tuc.ds2020.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "patient_id")
    UUID id;

    @OneToOne(cascade = CascadeType.REMOVE)
    User user;

    @OneToMany
    List<MedicationPlan> medicationPlans = new ArrayList<>();

    @OneToOne(cascade = CascadeType.REMOVE)
    MedicalRecord medicalRecord;

    @OneToMany(mappedBy = "patient")
    List<Activity> activities = new ArrayList<>();
}
