package concerttours.service.impl;
import concerttours.dao.BandDao;
import concerttours.model.BandModel;
import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@UnitTest
public class DefaultBandServiceUnitTest {
    private DefaultBandService bandService;
    private BandDao bandDao;
    private BandModel bandModel;

    private static final String TEST_BAND_CODE = "Ch00X";
    private static final String TEST_BAND_NAME = "Singers All";
    private static final String TEST_BAND_HISTORY = "Medieval choir formed in 2001, based in Munich famous for authentic monastic chants";

    @Before
    public void beforeTest() {
        bandService = new DefaultBandService();
        bandDao = mock(BandDao.class);
        bandService.setBandDao(bandDao);

        bandModel = new BandModel();
        bandModel.setCode(TEST_BAND_CODE);
        bandModel.setName(TEST_BAND_NAME);
        bandModel.setAlbumSales(null);
        bandModel.setHistory(TEST_BAND_HISTORY);
    }

    /**
     * Assert that method throws an exception when there are more than 1 items with the same code
     */
    @Test(expected = AmbiguousIdentifierException.class)
    public void testGetBandForCode_AmbigiousCode() {
        when(bandDao.getBandsByCode(TEST_BAND_CODE)).thenReturn(Arrays.asList(bandModel, bandModel));
        bandService.getBandForCode(TEST_BAND_CODE);
    }

    /**
     * Assert that correct band item is returned
     */
    @Test
    public void testGetBandForCode() {
        when(bandDao.getBandsByCode(TEST_BAND_CODE)).thenReturn(Collections.singletonList(bandModel));
        assertSame("Wrong band returned", bandModel, bandService.getBandForCode(TEST_BAND_CODE));
    }

    /**
     * Assert that method throws an exception for null argument
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBandForCode_NullCode() {
        //noinspection unchecked
        when(bandDao.getBandsByCode(null)).thenThrow(IllegalArgumentException.class);
        bandService.getBandForCode(null);
    }

    /**
     * Assert that the same list is returned as from dao
     */
    @Test
    public void testGetBands() {
        List<BandModel> testList = new ArrayList<>();
        when(bandDao.getAllBands()).thenReturn(testList);
        assertSame("Wrong list returned", testList, bandService.getBands());
    }
}