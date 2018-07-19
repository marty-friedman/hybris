package concerttours.events;
import de.hybris.platform.servicelayer.event.ClusterAwareEvent;
import de.hybris.platform.servicelayer.event.events.AbstractEvent;

public class BandAlbumSalesEvent extends AbstractEvent implements ClusterAwareEvent {
    private final String code;
    private final String name;
    private final Long sales;

    public BandAlbumSalesEvent(final String code, final String name, final Long sales) {
        this.code = code;
        this.name = name;
        this.sales = sales;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getSales() {
        return sales;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean publish(int i, int i1) {
        return (i == i1);
    }
}