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
@Table(name = "medication")
public class Medication {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "medication_id")
    UUID id;

    @Column(name = "name")
    String name;

    @ManyToMany
    @JoinTable
    List<SideEffect> sideEffects = new ArrayList<>();

    @Column(name = "dosage")
    Double dosage;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "medications")
    List<MedicationPlan> medicationPlan = new ArrayList<>();
}
