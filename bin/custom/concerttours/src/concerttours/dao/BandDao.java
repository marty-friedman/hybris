package concerttours.dao;

import concerttours.model.BandModel;

import java.util.List;

public interface BandDao {
    List<BandModel> getAllBands();
    List<BandModel> getBandsByCode(String code);
}
