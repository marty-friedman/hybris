package concerttours.attributehandlers;

import concerttours.model.ConcertModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ConcertDaysUntilAttributeHandler extends AbstractDynamicAttributeHandler<Long, ConcertModel> {
    @Override
    public Long get(final ConcertModel model) {
        if (model.getDate() == null)
            return null;
        final ZonedDateTime concertDateTime = model.getDate().toInstant().atZone(ZoneId.systemDefault());
        final ZonedDateTime currentDateTime = ZonedDateTime.now();
        if (concertDateTime.isBefore(currentDateTime))
            return 0L;
        final Duration duration = Duration.between(currentDateTime, concertDateTime);
        return duration.toDays();
    }
}
