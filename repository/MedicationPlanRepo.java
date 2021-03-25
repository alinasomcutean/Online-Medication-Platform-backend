package ro.tuc.ds2020.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.tuc.ds2020.model.MedicationPlan;

import java.util.UUID;

@Repository
public interface MedicationPlanRepo extends JpaRepository<MedicationPlan, UUID> {
}
