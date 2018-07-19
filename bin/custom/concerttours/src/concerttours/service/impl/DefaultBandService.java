package concerttours.service.impl;

import concerttours.dao.BandDao;
import concerttours.model.BandModel;
import concerttours.service.BandService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

public class DefaultBandService implements BandService {
    private BandDao bandDao;

    @Override
    public List<BandModel> getBands() {
        return bandDao.getAllBands();
    }

    @Override
    public BandModel getBandForCode(final String code) {
        final List<BandModel> bandModels = bandDao.getBandsByCode(code);
        if (bandModels.isEmpty())
            throw new UnknownIdentifierException("No band found for code: " + code);
        if (bandModels.size() > 1)
            throw new AmbiguousIdentifierException("Multiple bands existing for code: " + code);
        return bandModels.get(0);
    }

    public void setBandDao(final BandDao bandDao) {
        this.bandDao = bandDao;
    }
}
