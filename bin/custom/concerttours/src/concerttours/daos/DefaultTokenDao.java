package concerttours.daos;

import concerttours.model.TokenModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component(value = "tokenDao")
public class DefaultTokenDao implements TokenDao {
    @Resource
    private FlexibleSearchService flexibleSearchService;
    @Resource
    private ModelService modelService;

    @Override
    public List<TokenModel> getAllTokens() {
        String query = "SELECT {p:" + TokenModel.PK + "} FROM {" + TokenModel._TYPECODE + " AS p}";
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        return flexibleSearchService.<TokenModel>search(flexibleSearchQuery).getResult();
    }

    @Override
    public void save(TokenModel tokenModel) {
        modelService.save(tokenModel);
    }
}
