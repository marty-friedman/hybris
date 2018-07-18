package concerttours.facade.impl;

import concerttours.data.ConcertSummaryData;
import concerttours.data.TourData;
import concerttours.facade.TourFacade;
import concerttours.model.ConcertModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;

import java.util.Locale;
import java.util.stream.Collectors;

public class DefaultTourFacade implements TourFacade {
    private ProductService productService;

    @Override
    public TourData getTourDetails(String id) {
        ProductModel productModel = productService.getProductForCode(id);
        TourData tourData = new TourData();
        tourData.setName(productModel.getName(Locale.ENGLISH));
        tourData.setDescription(productModel.getDescription());
        tourData.setId(productModel.getCode());
        if (productModel.getVariants() != null)
            tourData.setConcerts(productModel.getVariants().stream()
                    .filter(variantProductModel -> variantProductModel instanceof ConcertModel)
                    .map(variantProductModel -> (ConcertModel) variantProductModel)
                    .map(concertModel -> {
                        ConcertSummaryData concertSummaryData = new ConcertSummaryData();
                        concertSummaryData.setDate(concertModel.getDate());
                        concertSummaryData.setId(concertModel.getCode());
                        concertSummaryData.setVenue(concertModel.getVenue());
                        if (concertModel.getConcertType() != null)
                            concertSummaryData.setType(concertModel.getConcertType().getCode());
                        return concertSummaryData;
                    }).collect(Collectors.toList())
            );
        return tourData;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
