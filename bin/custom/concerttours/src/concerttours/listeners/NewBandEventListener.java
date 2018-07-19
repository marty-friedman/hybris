package concerttours.listeners;

import concerttours.model.BandModel;
import concerttours.model.NewsModel;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Date;

public class NewBandEventListener extends AbstractEventListener<AfterItemCreationEvent> {
    private static final String HEADLINE_TEMPLATE = "New band, %s";
    private static final String CONTENT_TEMPLATE = "There's a new band called %s. The tour information will be available soon";

    private ModelService modelService;

    @Override
    protected void onEvent(AfterItemCreationEvent event) {
        if (event == null || event.getSource() == null)
            return;
        final Object object = modelService.get(event.getSource());
        if (!(object instanceof BandModel))
            return;
        final BandModel bandModel = (BandModel) object;
        final NewsModel news = modelService.create(NewsModel.class);
        news.setDate(new Date());
        news.setHeadline(String.format(HEADLINE_TEMPLATE, bandModel.getName()));
        news.setContent(String.format(CONTENT_TEMPLATE, bandModel.getName()));
        modelService.save(news);
    }

    public void setModelService(final ModelService modelService) {
        this.modelService = modelService;
    }
}
