package concerttours.service;

import concerttours.model.BandModel;

import java.util.List;
import java.util.Optional;

public interface BandService {
    List<BandModel> getAllBands();

    Optional<BandModel> getBandModelByName(String name);

    Optional<BandModel> getBandModelByCode(String code);
}
