package concerttours.dao.impl;

import concerttours.dao.BandDao;
import concerttours.model.BandModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("bandDao")
public class DefaultBandDao implements BandDao {
    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<BandModel> getAllBands() {
        final String queryString = "SELECT " + BandModel.PK + " FROM {" + BandModel._TYPECODE + "}";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<BandModel>search(query).getResult();
    }

    @Override
    public List<BandModel> getBandsByCode(final String code) {
        final String queryString = "SELECT " + BandModel.PK + " FROM {" + BandModel._TYPECODE + "} " +
                "WHERE {" + BandModel.CODE + "} = ?code";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        query.addQueryParameter("code", code);
        return flexibleSearchService.<BandModel>search(query).getResult();
    }
}
