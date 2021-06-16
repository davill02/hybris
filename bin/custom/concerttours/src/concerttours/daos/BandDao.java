package concerttours.daos;

import concerttours.model.BandModel;

import java.util.List;

public interface BandDao {
    List<BandModel> getAllBands();

    List<BandModel> findBandsByCode(String code);

    List<BandModel> findBandsByName(String name);
}
