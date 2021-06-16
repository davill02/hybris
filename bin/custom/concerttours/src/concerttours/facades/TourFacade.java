package concerttours.facades;

import concerttours.data.TourData;

public interface TourFacade {
    TourData getTourByCode(String code);
}
