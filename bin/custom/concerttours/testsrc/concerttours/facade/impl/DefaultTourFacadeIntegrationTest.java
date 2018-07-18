package concerttours.facade.impl;

import concerttours.data.TourData;
import concerttours.facade.TourFacade;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@IntegrationTest
public class DefaultTourFacadeIntegrationTest extends ServicelayerTransactionalTest {
    private static final String TEST_TOUR_ID = "201701";
    private static final String TEST_TOUR_NAME = "The Grand Little x Tour";
    private static final Integer TEST_TOUR_NUMBER_OF_CONCERTS = 6;
    private static final String TEST_TOUR_DESCRIPTION = null;

    @Resource
    private TourFacade tourFacade;

    /**
     * Assert that data is translated correctly
     */
    @Test
    public void testGetTourDetails() {
        try {
            importCsv("/impex/testdata-concerttours.impex", "utf-8");
        } catch (ImpExException e) {
            e.printStackTrace();
        }
        TourData tourData = tourFacade.getTourDetails(TEST_TOUR_ID);
        assertEquals("Name mismatch", TEST_TOUR_NAME, tourData.getName());
        assertEquals("Number of concerts mismatch", (long) TEST_TOUR_NUMBER_OF_CONCERTS, tourData.getConcerts().size());
        assertEquals("Description mismatch", TEST_TOUR_DESCRIPTION, tourData.getDescription());
    }

    /**
     * Assert that the exception is thrown when null passed
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetTourDetails_NullCode() {
        TourData tourData = tourFacade.getTourDetails(null);
    }

    /**
     * Assert that the exception is thrown when trying to find unexisting tour
     */
    @Test(expected = UnknownIdentifierException.class)
    public void testGetTourDetails_UnexistingCode() {
        TourData tourData = tourFacade.getTourDetails(TEST_TOUR_ID);
    }
}
