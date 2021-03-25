package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dto.SideEffectsDto;
import ro.tuc.ds2020.model.SideEffect;
import ro.tuc.ds2020.repository.SideEffectRepo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SideEffectsServiceImpl implements SideEffectsService {

    private final SideEffectRepo sideEffectRepo;

    @Override
    public SideEffect getByName(String name) {
        return sideEffectRepo.findByName(name);
    }

    @Override
    public List<SideEffectsDto> getSideEffects() {
        List<SideEffectsDto> sideEffectsDtos = new ArrayList<>();
        List<SideEffect> sideEffects = sideEffectRepo.findAll();
        sideEffects.forEach(s -> sideEffectsDtos.add(new SideEffectsDto(s)));
        return sideEffectsDtos;
    }
}
