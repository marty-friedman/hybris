package concerttours.attributehandlers;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Assert;
import org.junit.Test;
import concerttours.model.ConcertModel;

@IntegrationTest
public class ConcertDaysUntilAttributeHandlerIntegrationTest extends ServicelayerTransactionalTest {
    @Resource
    private ModelService modelService;

    /**
     * Assert that the method correctly calculates amount of days left to the future concert
     */
    @Test
    public void testGet_Future() {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        final Date futureDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        Assert.assertEquals("Wrong value for concert in the future: " + concert.getDaysUntil(), 2L, concert.getDaysUntil().longValue());
    }

    /**
     * Assert that the method returns null when concert date is null
     */
    @Test
    public void testGet_Null() {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        Assert.assertNull("No concert date does not return null: "+ concert.getDaysUntil(), concert.getDaysUntil());
    }

    /**
     * Assert that the method returns 0 when the concert had passed
     */
    @Test
    public void testGet_Past() {
        final ConcertModel concert = modelService.create(ConcertModel.class);
        final Date pastDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        concert.setDate(pastDate);
        Assert.assertEquals("Wrong value for concert in the past: " + concert.getDaysUntil(), 0L, concert.getDaysUntil().longValue());
    }
}