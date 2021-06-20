package concerttours.events;

import concerttours.model.BandModel;
import concerttours.model.NewsModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@IntegrationTest
public class BandAlbumSalesEventListenerIntTest extends ServicelayerTransactionalTest {
    @Resource
    private FlexibleSearchService flexibleSearchService;
    @Resource
    private ModelService modelService;
    private static final String BAND_CODE = "101-JAZ";
    private static final String BAND_NAME = "Tight Notes";
    private static final String BAND_HISTORY = "New contemporary, 7-piece Jaz unit from London, formed in 2015";
    private static final Long MANY_ALBUMS_SOLD = 1000000L;

    @Before
    public void setUp() throws Exception {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            new JdbcTemplate(Registry.getCurrentTenant().getDataSource()).execute("CHECKPOINT");
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException ignored) {
        }
        createCoreData();
        createDefaultCatalog();
    }

    @Test(expected = ModelSavingException.class)
    public void testValidationInterceptor() {
        final BandModel band = modelService.create(BandModel.class);
        band.setCode(BAND_CODE);
        band.setAlbumSales(-10L);
        modelService.save(band);
    }

    @Test
    public void testEventSending() {
        final BandModel band = getBandModel();

        modelService.save(band);
        final NewsModel news = findLastNews();

        assertNotNull(news);
        Assert.assertTrue("Unexpected news: " + news.getHeadline(), news.getHeadline().contains(BAND_NAME));
    }

    private BandModel getBandModel() {
        final BandModel band = modelService.create(BandModel.class);
        band.setCode(BAND_CODE);
        band.setName(BAND_NAME);
        band.setHistory(BAND_HISTORY);
        band.setAlbumSales(MANY_ALBUMS_SOLD);
        return band;
    }

    private NewsModel findLastNews() {
        String builder = "SELECT {n:" + NewsModel.PK + "} " +
                "FROM {" + NewsModel._TYPECODE + " AS n} " +
                "ORDER BY " + "{n:" + NewsModel.CREATIONTIME + "} DESC";
        final List<NewsModel> list = flexibleSearchService.<NewsModel>search(builder).getResult();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}