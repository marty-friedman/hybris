package concerttours.facade.impl;

import concerttours.data.BandData;
import concerttours.data.TourSummaryData;
import concerttours.enums.MusicType;
import concerttours.facade.BandFacade;
import concerttours.model.BandModel;
import concerttours.model.ConcertModel;
import concerttours.service.BandService;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultBandFacade implements BandFacade {
    private BandService bandService;

    @Override
    public List<BandData> getBands() {
        return bandService.getBands().stream().map(this::convertModelToData).collect(Collectors.toList());
    }

    @Override
    public BandData getBand(String id) {
        return convertModelToData(bandService.getBandForCode(id));
    }

    private BandData convertModelToData(BandModel bandModel) {
        BandData bandData = new BandData();
        bandData.setAlbumsSold(bandModel.getAlbumSales());
        bandData.setDescription(bandModel.getHistory());
        bandData.setId(bandModel.getCode());
        bandData.setName(bandModel.getName());
        if (bandModel.getTypes() != null)
            bandData.setGenres(bandModel.getTypes().stream().map(MusicType::getCode).collect(Collectors.toList()));
        if (bandModel.getTours() != null) {
            bandData.setTours(bandModel.getTours().stream().map(productModel -> {
                TourSummaryData tourSummaryData = new TourSummaryData();
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

    public void setBandService(BandService bandService) {
        this.bandService = bandService;
    }
}
