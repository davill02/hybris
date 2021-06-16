package concerttours.facades;

import concerttours.data.BandData;

import java.util.List;

public interface BandFacade {
    BandData getBandById(String id);

    List<BandData> getAllBands();
}
