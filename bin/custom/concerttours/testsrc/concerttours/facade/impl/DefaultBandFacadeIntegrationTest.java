package concerttours.facade.impl;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.facade.BandFacade;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.*;

import static org.junit.Assert.assertEquals;

@IntegrationTest
public class DefaultBandFacadeIntegrationTest extends ServicelayerTransactionalTest {
    private static final String TEST_BAND_ID = "A001";
    private static final String TEST_BAND_NAME = "yRock";
    private static final Long TEST_BAND_ALBUM_SALES = 1000_000L;
    private static final String TEST_BAND_DESCRIPTION = "Occasional tribute rock band comprising senior managers from a leading commerce software vendor";
    private static final Set<String> TEST_BAND_GENRES = new HashSet<>(Arrays.asList("jazz", "eighties"));
    private static final String TEST_BAND_TOUR_ID = "201701";
    private static final String TEST_BAND_TOUR_NAME = "The Grand Little x Tour";
    private static final Integer TEST_BAND_TOUR_NUMBER_OF_CONCERTS = 6;

    @Resource
    private BandFacade bandFacade;

    /**
     * Assert that test bands are added and returned by the method
     */
    @Test
    public void testGetBands() {
        List<BandData> initialBands = bandFacade.getBands();
        try {
            importCsv("/impex/testdata-concerttours.impex", "utf-8");
        } catch (ImpExException e) {
            e.printStackTrace();
        }
        List<BandData> updatedBands = bandFacade.getBands();
        assertEquals(initialBands.size()+2, updatedBands.size());
    }

    /**
     * Assert that test band data is translated correctly
     */
    @Test
    public void testGetBand() {
        try {
            importCsv("/impex/testdata-concerttours.impex", "utf-8");
        } catch (ImpExException e) {
            e.printStackTrace();
        }
        BandData testBand = bandFacade.getBand(TEST_BAND_ID);
        assertEquals("Name mismatch", TEST_BAND_NAME, testBand.getName());
        assertEquals("Description mismatch", TEST_BAND_DESCRIPTION, testBand.getDescription());
        assertEquals("Album sales mismatch", TEST_BAND_ALBUM_SALES, testBand.getAlbumsSold());
        assertEquals("Name mismatch", TEST_BAND_NAME, testBand.getName());
        assertEquals("Genres mismatch", TEST_BAND_GENRES, new HashSet<>(testBand.getGenres()));
        assertEquals("Tours number mismatch", 1, testBand.getTours().size());
        TourSummaryData tourSummaryData = testBand.getTours().get(0);
        assertEquals("Tour id mismatch", TEST_BAND_TOUR_ID, tourSummaryData.getId());
        assertEquals("Tour name mismatch", TEST_BAND_TOUR_NAME, tourSummaryData.getName());
        assertEquals("Tour number of concerts mismatch", TEST_BAND_TOUR_NUMBER_OF_CONCERTS, tourSummaryData.getNumberOfConcerts());
    }

    /**
     * Assert that the exception is thrown if no band is found
     */
    @Test(expected = UnknownIdentifierException.class)
    public void testGetBand_UnexistingCode() {
        BandData testBand = bandFacade.getBand(TEST_BAND_ID);
    }

    /**
     * Assert that the eception is thrown if null code passed
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBand_NullCode() {
        BandData testBand = bandFacade.getBand(null);
    }
}
