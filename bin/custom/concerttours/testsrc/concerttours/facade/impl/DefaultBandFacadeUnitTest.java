package concerttours.facade.impl;

import concerttours.data.BandData;
import concerttours.model.BandModel;
import concerttours.service.BandService;
import de.hybris.bootstrap.annotations.UnitTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
public class DefaultBandFacadeUnitTest {
    private static final String TEST_BAND_ID = "A001";
    private static final String TEST_BAND_NAME = "yRock";
    private static final Long TEST_BAND_ALBUM_SALES = 1000_000L;
    private static final String TEST_BAND_DESCRIPTION = "Occasional tribute rock band comprising senior managers from a leading commerce software vendor";

    private DefaultBandFacade bandFacade;
    private BandService bandService;
    private BandModel bandModel;

    @Before
    public void beforeTest() {
        bandFacade = new DefaultBandFacade();
        bandService = mock(BandService.class);
        bandFacade.setBandService(bandService);

        bandModel = new BandModel();
        bandModel.setAlbumSales(TEST_BAND_ALBUM_SALES);
        bandModel.setHistory(TEST_BAND_DESCRIPTION);
        bandModel.setName(TEST_BAND_NAME);
        bandModel.setCode(TEST_BAND_ID);
    }

    /**
     * Assert that data is translated correctly
     */
    @Test
    public void testGetBand() {
        when(bandService.getBandForCode(TEST_BAND_ID)).thenReturn(bandModel);
        BandData bandData = bandFacade.getBand(TEST_BAND_ID);
        assertEquals("Id mismatch", TEST_BAND_ID, bandData.getId());
        assertEquals("Description mismatch", TEST_BAND_DESCRIPTION, bandData.getDescription());
        assertEquals("Name mismatch", TEST_BAND_NAME, bandData.getName());
        assertEquals("Album sales mismatch", TEST_BAND_ALBUM_SALES, bandData.getAlbumsSold());
    }

    /**
     * Assert that method returns same number of instances as by the service's
     */
    @Test
    public void testGetBands() {
        when(bandService.getBands()).thenReturn(Arrays.asList(bandModel, bandModel, bandModel));
        assertEquals("Wrong number of instances returned", 3, bandFacade.getBands().size());
    }
}
