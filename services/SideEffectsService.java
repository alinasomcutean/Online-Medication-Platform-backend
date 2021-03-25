package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dto.SideEffectsDto;
import ro.tuc.ds2020.model.SideEffect;

import java.util.List;

public interface SideEffectsService {

    SideEffect getByName(String name);

    List<SideEffectsDto> getSideEffects();
}
