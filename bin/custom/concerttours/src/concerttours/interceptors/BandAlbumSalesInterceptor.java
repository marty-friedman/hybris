package concerttours.interceptors;

import concerttours.events.BandAlbumSalesEvent;
import concerttours.model.BandModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.interceptor.InterceptorContext;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.PrepareInterceptor;
import de.hybris.platform.servicelayer.interceptor.ValidateInterceptor;

import javax.annotation.Resource;

import static de.hybris.platform.servicelayer.model.ModelContextUtils.getItemModelContext;

public class BandAlbumSalesInterceptor implements ValidateInterceptor, PrepareInterceptor {
    private static final Long BIG_ALBUM_SALES = 50_000L;

    @Resource
    private EventService eventService;

    @Override
    public void onPrepare(final Object o, final InterceptorContext ctx) throws InterceptorException {
        if (!(o instanceof BandModel))
            return;
        final BandModel bandModel = (BandModel) o;
        if (bandModel.getAlbumSales() == null)
            return;
        if (albumSalesGrownBig(bandModel, ctx))
            eventService.publishEvent(new BandAlbumSalesEvent(bandModel.getCode(), bandModel.getName(), bandModel.getAlbumSales()));
    }

    @Override
    public void onValidate(final Object o, final InterceptorContext ctx) throws InterceptorException {
        if (!(o instanceof BandModel))
            return;
        final BandModel bandModel = (BandModel) o;
        final Long albumSales = bandModel.getAlbumSales();
        if (albumSales != null && albumSales < 0L)
            throw new InterceptorException("Album sales must be positive integer value");
    }

    private boolean albumSalesGrownBig(final BandModel model, final InterceptorContext ctx) {
        if (model.getAlbumSales() > BIG_ALBUM_SALES) {
            if (ctx.isNew(model))
                return true;
            else {
                final Long originalSales = getItemModelContext(model).getOriginalValue(BandModel.ALBUMSALES);
                return originalSales == null || originalSales < BIG_ALBUM_SALES;
            }
        }
        return false;
    }
}
