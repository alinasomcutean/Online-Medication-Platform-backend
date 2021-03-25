package ro.tuc.ds2020.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
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
@Table(name = "medication_plan")
public class MedicationPlan {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "medication_plan_id")
    UUID id;

    @Column(name = "intake_intervals")
    Integer intakeIntervals;

    @Column(name = "period_start")
    LocalDate periodStart;

    @Column(name = "period_end")
    LocalDate periodEnd;

    @ManyToMany
    @JoinTable
    List<Medication> medications = new ArrayList<>();
}
