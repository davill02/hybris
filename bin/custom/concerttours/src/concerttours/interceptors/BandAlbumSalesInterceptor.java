package concerttours.interceptors;

import concerttours.events.BandAlbumSalesEvent;
import concerttours.model.BandModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import javax.annotation.Resource;

import static de.hybris.platform.servicelayer.model.ModelContextUtils.getItemModelContext;

public class BandAlbumSalesInterceptor implements ValidateInterceptor<BandModel>, PrepareInterceptor<BandModel> {
    private static final long BIG_SALES = 50000L;
    private static final long NEGATIVE_SALES = 0L;
    @Resource
    private EventService eventService;


    private boolean hasBecomeBig(final BandModel band, final InterceptorContext ctx) {
        final Long sales = band.getAlbumSales();
        if (sales != null && sales >= BIG_SALES) {
            if (ctx.isNew(band)) {
                return true;
            } else {
                final Long oldValue = getItemModelContext(band).getOriginalValue(BandModel.ALBUMSALES);
                return oldValue == null || oldValue.intValue() < BIG_SALES;
            }
        }
        return false;
    }

    @Override
    public void onValidate(BandModel bandModel, InterceptorContext interceptorContext) throws InterceptorException {
        final Long sales = bandModel.getAlbumSales();
        if (sales != null && sales < NEGATIVE_SALES) {
            throw new InterceptorException("Album sales must be positive");
        }
    }

    @Override
    public void onPrepare(BandModel bandModel, InterceptorContext interceptorContext) throws InterceptorException {
        if (hasBecomeBig(bandModel, interceptorContext)) {
            eventService.publishEvent(new BandAlbumSalesEvent(bandModel.getCode(), bandModel.getName(), bandModel.getAlbumSales()));
        }
    }
}