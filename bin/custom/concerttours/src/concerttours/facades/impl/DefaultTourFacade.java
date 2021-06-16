package concerttours.facades.impl;

import concerttours.data.ConcertSummaryData;
import concerttours.data.TourData;
import concerttours.enums.ConcertType;
import concerttours.facades.TourFacade;
import concerttours.model.ConcertModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.variants.model.VariantProductModel;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class DefaultTourFacade implements TourFacade {
    @Resource
    private ProductService productService;

    @Override
    public TourData getTourByCode(String id) {
        TourData tourData = new TourData();
        final ProductModel product = productService.getProductForCode(id);
        if (product != null) {
            setConcertsToData(tourData, product);
            tourData.setId(product.getCode());
            tourData.setTourName(product.getName());
            tourData.setDescription(product.getDescription());
        }
        return tourData;
    }

    private void setConcertsToData(TourData tourData, ProductModel product) {
        final List<ConcertSummaryData> concerts = new ArrayList<>();
        if (product.getVariants() != null) {
            for (final VariantProductModel variant : product.getVariants()) {
                if (variant instanceof ConcertModel) {
                    final ConcertModel concert = (ConcertModel) variant;
                    final ConcertSummaryData summary = new ConcertSummaryData();
                    summary.setId(concert.getCode());
                    summary.setDate(concert.getDate());
                    summary.setVenue(concert.getVenue());
                    summary.setType(concert.getConcertType() == ConcertType.OPENAIR ? "Outdoors" : "Indoors");
                    setActorsToSummaryConcertData(concert, summary);
                    concerts.add(summary);
                }
            }
        }
        tourData.setConcerts(concerts);
    }

    private void setActorsToSummaryConcertData(ConcertModel concert, ConcertSummaryData summary) {
        List<String> openingActors = new ArrayList<>();
        concert.getOpeningActors().forEach(openingActorModel -> openingActors.add(openingActorModel.getName()));
        summary.setOpeningActors(openingActors);
    }
}
