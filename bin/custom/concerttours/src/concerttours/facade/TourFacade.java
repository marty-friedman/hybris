package concerttours.facade;

import concerttours.data.TourData;

public interface TourFacade {
    TourData getTourDetails(String id);
}
