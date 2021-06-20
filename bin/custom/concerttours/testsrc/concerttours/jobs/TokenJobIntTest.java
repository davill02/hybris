package concerttours.jobs;

import concerttours.model.TokenModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.core.Registry;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@IntegrationTest
public class TokenJobIntTest extends ServicelayerTransactionalTest {
    @Resource
    private ModelService modelService;
    @Resource
    private ChangeTokenValueJob changeTokenValueJob;
    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Before
    public void setUp() throws Exception {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            new JdbcTemplate(Registry.getCurrentTenant().getDataSource()).execute("CHECKPOINT");
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException exc) {
        }
    }

    @Test
    public void shouldFailJob() {
        String query = "SELECT {p:" + TokenModel.PK + "} FROM {" + TokenModel._TYPECODE + " AS p}";
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        List<TokenModel> tokens = flexibleSearchService.<TokenModel>search(flexibleSearchQuery).getResult();
        modelService.removeAll(tokens);

        PerformResult performResult = changeTokenValueJob.perform(null);

        assertEquals(CronJobResult.FAILURE, performResult.getResult());
    }

    @Test
    public void shouldSuccessJob() {
        TokenModel tokenModel = modelService.create(TokenModel.class);
        tokenModel.setToken(UUID.randomUUID().toString());
        modelService.save(tokenModel);

        PerformResult performResult = changeTokenValueJob.perform(null);

        assertEquals(CronJobResult.SUCCESS, performResult.getResult());
    }
}
