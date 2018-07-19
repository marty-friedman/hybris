package concerttours.attributehandlers;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import concerttours.model.ConcertModel;
import de.hybris.bootstrap.annotations.UnitTest;

@UnitTest
public class ConcertDaysUntilAttributeHandlerUnitTest {
    /**
     * Assert that the method correctly calculates amount of days left to the future concert
     */
    @Test
    public void testGet_Future() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        final Date futureDate = new Date(new Date().getTime() + 49 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        Assert.assertEquals("Wrong value for concert in the future", 2L, handler.get(concert).longValue());
    }

    /**
     * Assert that the method returns null when concert date is null
     */
    @Test
    public void testGet_Null() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        Assert.assertNull(handler.get(concert));
    }

    /**
     * Assert that the method returns 0 when the concert had passed
     */
    @Test
    public void testGet_Past() {
        final ConcertModel concert = new ConcertModel();
        final ConcertDaysUntilAttributeHandler handler = new ConcertDaysUntilAttributeHandler();
        final Date futureDate = new Date(new Date().getTime() - 24 * 60 * 60 * 1000);
        concert.setDate(futureDate);
        Assert.assertEquals("Wrong value for concert in the past", 0L, handler.get(concert).longValue());
    }
}