package concerttours.daos;

import concerttours.model.BandModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component(value = "bandDao")
public class DefaultBandDao implements BandDao {
    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<BandModel> getAllBands() {
        String query = "SELECT {p:" + BandModel.PK + "} FROM {" + BandModel._TYPECODE + " AS p}";
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        return flexibleSearchService.<BandModel>search(flexibleSearchQuery).getResult();
    }

    @Override
    public List<BandModel> findBandsByCode(String code) {
        final String queryString = //
                "SELECT {p:" + BandModel.PK + "}" //
                        + "FROM {" + BandModel._TYPECODE + " AS p} "//
                        + "WHERE " + "{p:" + BandModel.CODE + "}=?code ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("code", code);
        return flexibleSearchService.<BandModel>search(query).getResult();
    }

    @Override
    public List<BandModel> findBandsByName(String name) {
        String query = "SELECT {p:" + BandModel.PK + "} FROM {" + BandModel._TYPECODE + " AS p} " +
                " WHERE {p." + BandModel.NAME + "=?name}";
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query);
        flexibleSearchQuery.addQueryParameter("name", name);
        return flexibleSearchService.<BandModel>search(query).getResult();
    }
}
