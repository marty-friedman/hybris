package concerttours.events;
import concerttours.model.BandModel;
import concerttours.model.NewsModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTest;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@IntegrationTest
public class BandAlbumSalesEventListenerIntegrationTest extends ServicelayerTest {
    private static final String BAND_CODE = "101-JAZ";
    private static final String BAND_NAME = "Tight Notes";
    private static final String BAND_HISTORY = "New contemporary, 7-piece Jaz unit from London, formed in 2015";
    private static final Long MANY_ALBUMS_SOLD = 1000000L;

    @Resource
    private FlexibleSearchService flexibleSearchService;
    @Resource
    private ModelService modelService;

    @Before
    public void beforeTest() throws Exception {
        createCoreData();
        createDefaultCatalog();
    }

    /**
     * Assert that the exception is thrown when saving band with negative album sales value
     */
    @Test(expected = ModelSavingException.class)
    public void testValidationInterceptor() {
        final BandModel band = modelService.create(BandModel.class);
        band.setCode(BAND_CODE);
        band.setAlbumSales(-10L);
        modelService.save(band);
    }

    /**
     * Assert that the news are publishing when saving a band with hube amount of album sales
     */
    //@Test
    public void testEventSending() {
        final BandModel band = modelService.create(BandModel.class);
        band.setCode(BAND_CODE);
        band.setName(BAND_NAME);
        band.setHistory(BAND_HISTORY);
        band.setAlbumSales(MANY_ALBUMS_SOLD);
        modelService.save(band);
        final NewsModel news = findLastNews();
        assertNotNull("News are null", news);
        assertTrue("Unexpected news: " + news.getHeadline(), news.getHeadline().contains(BAND_NAME));
    }

    /**
     * Assert that the news are publishing when saving a band with hube amount of album sales
     */
    @Test
    public void testEventSendingAsync() throws Exception {
        final BandModel band = modelService.create(BandModel.class);
        band.setCode(BAND_CODE);
        band.setName(BAND_NAME);
        band.setHistory(BAND_HISTORY);
        band.setAlbumSales(MANY_ALBUMS_SOLD);
        modelService.save(band);

        Thread.sleep(2000L);
        final NewsModel news = findLastNews();
        assertNotNull("News are null", news);
        assertTrue("Unexpected news: " + news.getHeadline(), news.getHeadline().contains(BAND_NAME));
    }

    private NewsModel findLastNews() {
        String query = "SELECT {n:" + NewsModel.PK + "} " +
                "FROM {" + NewsModel._TYPECODE + " AS n} " +
                "ORDER BY " + "{n:" + NewsModel.CREATIONTIME + "} DESC";
        final List<NewsModel> list = flexibleSearchService.<NewsModel> search(query).getResult();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
}