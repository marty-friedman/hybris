package concerttours.service;

import concerttours.model.BandModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

public interface BandService {
    List<BandModel> getBands();
    BandModel getBandForCode(String code) throws UnknownIdentifierException, AmbiguousIdentifierException, IllegalArgumentException;
}
