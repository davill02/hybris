package concerttours.facades.impl;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.facades.BandFacade;
import concerttours.model.BandModel;
import concerttours.service.BandService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class DefaultBandFacade implements BandFacade {
    @Resource
    private BandService bandService;

    @Override
    public BandData getBandById(String id) {
        BandData data = new BandData();
        Optional<BandModel> optModel = bandService.getBandModelByCode(id);
        if (optModel.isPresent()) {
            BandModel model = optModel.get();
            fillData(data, model);
        }
        return data;
    }

    private void fillData(BandData data, BandModel model) {
        data.setId(model.getCode());
        data.setName(model.getName());
        data.setDescription(model.getHistory());
        data.setAlbumsSold(model.getAlbumSales());
        setGenresToData(data, model);
        setToursToData(data, model);
    }

    private void setToursToData(BandData data, BandModel model) {
        final List<TourSummaryData> tourHistory = new ArrayList<>();
        if (model.getTours() != null) {
            model.getTours().forEach(tour -> {
                final TourSummaryData summary = new TourSummaryData();
                summary.setId(tour.getCode());
                summary.setTourName(tour.getName(Locale.ENGLISH));
                summary.setNumberOfConcerts(Integer.toString(tour.getVariants().size()));
                tourHistory.add(summary);
            });
        }
        data.setTours(tourHistory);
    }

    private void setGenresToData(BandData data, BandModel model) {
        List<String> genres = new ArrayList<>();
        if (model.getTypes() != null) {
            model.getTypes().forEach(musicType -> genres.add(musicType.getCode()));
        }
        data.setGenres(genres);
    }

    @Override
    public List<BandData> getAllBands() {
        List<BandData> bandDataList = new ArrayList<>();
        bandService.getAllBands().forEach(bandModel -> {
            BandData bandData = new BandData();
            fillData(bandData, bandModel);
            bandDataList.add(bandData);
        });
        return bandDataList;
    }
}
