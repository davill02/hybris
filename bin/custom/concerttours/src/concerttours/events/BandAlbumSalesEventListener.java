package concerttours.events;

import concerttours.model.NewsModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;
import java.util.Date;

public class BandAlbumSalesEventListener extends AbstractEventListener<BandAlbumSalesEvent> {
    private static final String BAND_SALES_HEADLINE = "concerttours.band.sale.headline";
    private static final String BAND_SALES_CONTENT = "concerttours.band.sale.content";
    @Resource
    private ConfigurationService configurationService;
    private ModelService modelService;

    public ModelService getModelService() {
        return modelService;
    }

    @Required
    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    protected void onEvent(final BandAlbumSalesEvent event) {
        if (event != null) {
            String bandDefaultHeadline = configurationService.getConfiguration().getString(BAND_SALES_HEADLINE);
            final String headline = String.format(bandDefaultHeadline, event.getName());
            String bandDefaultContent = configurationService.getConfiguration().getString(BAND_SALES_CONTENT);
            final String content = String.format(bandDefaultContent, event.getName(), event.getSales());
            final NewsModel news = modelService.create(NewsModel.class);
            news.setDate(new Date());
            news.setHeadline(headline);
            news.setContent(content);
            modelService.save(news);
        }
    }
}