package ro.tuc.ds2020.services.rpc;

import ro.tuc.ds2020.dto.MedicationDto;
import ro.tuc.ds2020.dto.MedicationPillDispenserDto;
import ro.tuc.ds2020.dto.MedicationPlanPillDispenserDto;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface PillDispenser {

    List<MedicationPlanPillDispenserDto> getMedicationPlansForPatient(String patientId) throws Exception;

    List<MedicationPillDispenserDto> getMedicationsForMedicationPlan(String patientId, String medicationPlanId) throws Exception;

    void medicationTaken(String patientId, String medicationName) throws Exception;

    void allMedicationTaken(String message) throws Exception;
}
