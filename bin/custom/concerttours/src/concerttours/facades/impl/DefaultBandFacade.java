package concerttours.facades.impl;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.enums.MusicType;
import concerttours.facades.BandFacade;
import concerttours.model.BandModel;
import concerttours.service.BandService;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.media.MediaService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultBandFacade implements BandFacade {
    public static final String MEDIA_FORMAT_BAND_DETAIL = "bandDetail";
    public static final String MEDIA_FORMAT_BAND_LIST = "bandList";
    @Resource
    private BandService bandService;
    @Resource
    private MediaService mediaService;

    @Override
    public BandData getBandById(String id) {
        BandData data = new BandData();
        Optional<BandModel> optModel = bandService.getBandModelByCode(id);
        if (optModel.isPresent()) {
            BandModel model = optModel.get();
            fillData(data, model, MEDIA_FORMAT_BAND_DETAIL);
        }
        return data;
    }

    protected String getImageUrl(final BandModel sm, final MediaFormatModel format) {
        final MediaContainerModel container = sm.getImage();
        if (container != null) {
            return mediaService.getMediaByFormat(container, format).getDownloadURL();
        }
        return null;
    }

    private void fillData(BandData data, BandModel model, String mediaFormat) {
        data.setImageUrl(getImageUrl(model, mediaService.getFormat(mediaFormat)));
        data.setId(model.getCode());
        data.setName(model.getName());
        data.setDescription(model.getHistory());
        data.setAlbumsSold(model.getAlbumSales());
        setGenresToData(data, model);
        setToursToData(data, model);
    }

    private void setToursToData(BandData data, BandModel model) {
        final List<TourSummaryData> tourHistory = model.getTours().stream()
                .filter(Objects::nonNull)
                .map(this::createTourSummaryData)
                .collect(Collectors.toList());
        data.setTours(tourHistory);
    }

    private TourSummaryData createTourSummaryData(ProductModel tour) {
        final TourSummaryData summary = new TourSummaryData();
        summary.setId(tour.getCode());
        summary.setTourName(tour.getName(Locale.ENGLISH));
        summary.setNumberOfConcerts(Integer.toString(tour.getVariants().size()));
        return summary;
    }

    private void setGenresToData(BandData data, BandModel model) {
        List<String> genres = new ArrayList<>();
        if (model.getTypes() != null) {
            genres = model.getTypes().stream()
                    .map(MusicType::getCode)
                    .collect(Collectors.toList());
        }
        data.setGenres(genres);
    }

    @Override
    public List<BandData> getAllBands() {
        return bandService.getAllBands().stream()
                .map(bandModel -> createBandData(bandModel, MEDIA_FORMAT_BAND_LIST))
                .collect(Collectors.toList());
    }

    private BandData createBandData(BandModel bandModel, String mediaFormat) {
        BandData bandData = new BandData();
        fillData(bandData, bandModel, mediaFormat);
        return bandData;
    }
}
