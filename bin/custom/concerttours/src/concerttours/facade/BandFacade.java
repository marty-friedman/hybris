package concerttours.facade;

import concerttours.data.BandData;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

public interface BandFacade {
    List<BandData> getBands();
    BandData getBand(String id) throws UnknownIdentifierException, AmbiguousIdentifierException, IllegalArgumentException;
}
