package concerttours.constraints;

import concerttours.model.ConcertModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.validation.exceptions.HybrisConstraintViolation;
import de.hybris.platform.validation.services.ValidationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Set;

@IntegrationTest
public class HybrisVenueConstraintsIntTest extends ServicelayerTransactionalTest {
    @Resource
    private ModelService modelService;
    @Resource
    private ValidationService validationService;

    @Before
    public void setup() throws Exception {
        createCoreData();
        importCsv("/impex/essentialdata-constraints.impex", "UTF-8");
        validationService.reloadValidationEngine();
    }

    @Test
    public void shouldValidateInvalidConcert() {
        ConcertModel model = modelService.create(ConcertModel.class);
        model.setName("Invalid Concert");
        model.setVenue("InvalidVenue");

        Set<HybrisConstraintViolation> violations = validationService.validate(model);

        Assert.assertEquals(1, violations.size());
    }
}
