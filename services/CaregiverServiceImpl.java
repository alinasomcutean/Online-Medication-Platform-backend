package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.CaregiverDto;
import ro.tuc.ds2020.dto.PatientDto;
import ro.tuc.ds2020.dto.UserFrontDto;
import ro.tuc.ds2020.model.Caregiver;
import ro.tuc.ds2020.model.User;
import ro.tuc.ds2020.repository.CaregiverRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CaregiverServiceImpl implements CaregiverService {

    private final UserService userService;
    private final CaregiverRepo caregiverRepo;

    @Override
    public List<Caregiver> findAllCaregivers() {
        return caregiverRepo.findAll();
    }

    @Override
    public List<CaregiverDto> findCaregiversDtos() {
        //Find all the caregivers from db
        List<Caregiver> caregivers = findAllCaregivers();

        //Transform each caregiver from entity to dto in order to display them
        List<CaregiverDto> caregiverDtos = new ArrayList<>();
        caregivers.forEach(c -> caregiverDtos.add(new CaregiverDto(c)));

        return caregiverDtos;
    }

    @Override
    //Create a new caregiver
    public CaregiverDto createCaregiver(UserFrontDto userFrontDto) {
        String[] address = userFrontDto.getAddress().split(" ");

        //Create a new user
        User user = userService.createUser(userFrontDto.getFirstName(), userFrontDto.getLastName(), userFrontDto.getDateBirth(), userFrontDto.getGender(),
                userFrontDto.getUsername(), userFrontDto.getPassword(), address[0], Integer.parseInt(address[2]), address[3], address[4]);

        //Create a new caregiver and save it
        Caregiver caregiver = Caregiver.builder().user(user).patients(new ArrayList<>()).build();
        caregiverRepo.save(caregiver);
        return new CaregiverDto(caregiver);
    }

    @Override
    //Update the information regarding the user for a caregiver
    public CaregiverDto updateCaregiver(UserFrontDto userFrontDto) {
        //Find the caregiver by id
        Caregiver caregiver = caregiverRepo.findById(userFrontDto.getId()).orElseThrow(RuntimeException::new);

        //Update the caregiver info regarding the user
        caregiver.setUser(userService.updateUser(caregiver.getUser(), userFrontDto));
        caregiverRepo.save(caregiver);

        return new CaregiverDto(caregiver);
    }

    @Override
    //Delete an existing caregiver
    public void deleteCaregiver(UUID id) {
        caregiverRepo.deleteById(id);
    }

    @Override
    //Find all the patients of a given caregiver
    public List<PatientDto> findAssociatedPatients(UUID caregiverId) {
        //Find the caregiver object from db
        Caregiver caregiver = caregiverRepo.findById(caregiverId).orElseThrow(RuntimeException::new);

        //Create a list with all the associated patients in order to display them
        List<PatientDto> patientDtos = new ArrayList<>();
        caregiver.getPatients().forEach(p -> patientDtos.add(new PatientDto(p)));

        return patientDtos;
    }
}
