package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.CaregiverDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.Caregiver;

import java.util.List;
import java.util.UUID;

public interface CaregiverService {

    List<Caregiver> findAllCaregivers();

    List<CaregiverDto> findCaregiversDtos();

    CaregiverDto createCaregiver(UserFrontDto userFrontDto);

    CaregiverDto updateCaregiver(UserFrontDto userFrontDto);

    void deleteCaregiver(UUID id);

    List<PatientDto> findAssociatedPatients(UUID caregiverId);
}
