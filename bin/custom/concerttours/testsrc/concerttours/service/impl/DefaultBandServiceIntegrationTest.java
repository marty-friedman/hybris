package concerttours.service.impl;

import concerttours.model.BandModel;
import concerttours.service.BandService;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@IntegrationTest
public class DefaultBandServiceIntegrationTest extends ServicelayerTransactionalTest {
    private static final String TEST_BAND_NAME = "TEST_BAND_NAME";
    private static final String TEST_BAND_CODE = "TEST_BAND_CODE";
    private static final String TEST_BAND_HISTORY = "TEST_BAND_HISTORY";
    private static final Long TEST_BAND_ALBUM_SALES = 1000_000L;

    private BandModel testBand;

    @Resource
    private ModelService modelService;
    @Resource
    private BandService bandService;

    @Before
    public void beforeTest() {
        testBand = modelService.create(BandModel.class);
        testBand.setCode(TEST_BAND_CODE);
        testBand.setName(TEST_BAND_NAME);
        testBand.setHistory(TEST_BAND_HISTORY);
        testBand.setAlbumSales(TEST_BAND_ALBUM_SALES);
    }

    /**
     * Assert that newly added test band appears in bands list
     */
    @Test
    public void testGetBands() {
        List<BandModel> initialBands = bandService.getBands();
        Optional<BandModel> testInitialBands = initialBands.stream()
                .filter(bandModel -> TEST_BAND_CODE.equals(bandModel.getCode()))
                .findAny();
        assertFalse("Test band is present initially", testInitialBands.isPresent());
        modelService.save(testBand);
        List<BandModel> updatedBands = bandService.getBands();
        Optional<BandModel> bandModelOptional = updatedBands.stream()
                .filter(bandModel -> TEST_BAND_CODE.equals(bandModel.getCode()))
                .findAny();

        assertEquals("Number of bands must have increased by 1 after saving the test one",
                initialBands.size() + 1, updatedBands.size());
        assertTrue("Test band was not found", bandModelOptional.isPresent());

        BandModel actualBand = bandModelOptional.get();

        assertEquals("Code mismatch", TEST_BAND_CODE, actualBand.getCode());
        assertEquals("Name mismatch", TEST_BAND_NAME, actualBand.getName());
        assertEquals("History mismatch", TEST_BAND_HISTORY, actualBand.getHistory());
        assertEquals("Album sales mismatch", TEST_BAND_ALBUM_SALES, actualBand.getAlbumSales());
    }

    /**
     * Assert that test band can be searched through getBandForCode mathod
     */
    @Test
    public void testGetBandForCode() {
        List<BandModel> initialBands = bandService.getBands();
        Optional<BandModel> testInitialBands = initialBands.stream()
                .filter(bandModel -> TEST_BAND_CODE.equals(bandModel.getCode()))
                .findAny();
        assertFalse("Test band is present initially", testInitialBands.isPresent());
        modelService.save(testBand);
        BandModel actualBand = bandService.getBandForCode(TEST_BAND_CODE);
        assertEquals("Code mismatch", TEST_BAND_CODE, actualBand.getCode());
        assertEquals("Name mismatch", TEST_BAND_NAME, actualBand.getName());
        assertEquals("History mismatch", TEST_BAND_HISTORY, actualBand.getHistory());
        assertEquals("Album sales mismatch", TEST_BAND_ALBUM_SALES, actualBand.getAlbumSales());
    }

    /**
     * Assert that method throws an exception when null arg passed
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetBandForCode_NullCode() {
        bandService.getBandForCode(null);
    }

    /**
     * Assert that method throws an exception when trying to find unexisting band
     */
    @Test(expected = UnknownIdentifierException.class)
    public void testGetBandForCode_UnexistingCode() {
        bandService.getBandForCode(TEST_BAND_CODE);
    }
}
