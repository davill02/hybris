package concerttours.interceptors;

import concerttours.model.NewsModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import org.springframework.beans.factory.annotation.Required;

import javax.annotation.Resource;

public class NewsCatalogAwareInterceptor implements PrepareInterceptor<NewsModel> {
    @Resource
    private CatalogVersionService catalogVersionService;
    private String defaultCatalog;
    private String defaultCatalogVersion;

    @Override
    public void onPrepare(NewsModel newsModel, InterceptorContext interceptorContext) throws InterceptorException {
        if (newsModel.getCatalogVersion() == null) {
            newsModel.setCatalogVersion(catalogVersionService.getCatalogVersion(defaultCatalog, defaultCatalogVersion));
        }
    }

    @Required
    public void setDefaultCatalog(String defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
    }

    @Required
    public void setDefaultCatalogVersion(String defaultCatalogVersion) {
        this.defaultCatalogVersion = defaultCatalogVersion;
    }
}
