package concerttours.facade.impl;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.enums.MusicType;
import concerttours.facade.BandFacade;
import concerttours.model.BandModel;
import concerttours.model.ConcertModel;
import concerttours.service.BandService;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.servicelayer.media.MediaService;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultBandFacade implements BandFacade {
    private static final String LIST_MEDIA_FORMAT = "bandListMediaFormat";
    private static final String DETAILS_MEDIA_FORMAT = "bandDetailsMediaFormat";

    private BandService bandService;
    private MediaService mediaService;

    @Override
    public List<BandData> getBands() {
        return bandService.getBands().stream()
                .map(bandModel -> convertModelToData(bandModel, LIST_MEDIA_FORMAT))
                .collect(Collectors.toList());
    }

    @Override
    public BandData getBand(final String id) {
        return convertModelToData(bandService.getBandForCode(id), DETAILS_MEDIA_FORMAT);
    }

    private BandData convertModelToData(final BandModel bandModel, String mediaFormat) {
        final BandData bandData = new BandData();
        bandData.setAlbumsSold(bandModel.getAlbumSales());
        bandData.setDescription(bandModel.getHistory());
        bandData.setId(bandModel.getCode());
        bandData.setName(bandModel.getName());
        bandData.setImageUrl(getImageUrl(bandModel, mediaFormat));
        if (bandModel.getTypes() != null)
            bandData.setGenres(bandModel.getTypes().stream().map(MusicType::getCode).collect(Collectors.toList()));
        if (bandModel.getTours() != null) {
            bandData.setTours(bandModel.getTours().stream().map(productModel -> {
                final TourSummaryData tourSummaryData = new TourSummaryData();
                tourSummaryData.setId(productModel.getCode());
                tourSummaryData.setName(productModel.getName());
                tourSummaryData.setNumberOfConcerts(
                        (int) productModel.getVariants().stream()
                                .filter(variantProductModel -> variantProductModel instanceof ConcertModel)
                                .count()
                );
                return tourSummaryData;
            }).collect(Collectors.toList()));
        }
        return bandData;
    }

    private String getImageUrl(BandModel model, String format) {
        MediaContainerModel container = model.getImage();
        if (container == null)
            return null;
        return mediaService.getMediaByFormat(container, mediaService.getFormat(format)).getDownloadURL();
    }

    public void setBandService(final BandService bandService) {
        this.bandService = bandService;
    }

    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }
}
