package concerttours.dao;

import concerttours.model.NewsModel;

import java.util.Date;
import java.util.List;

public interface NewsDao {
    List<NewsModel> getNewsForDay(Date date);
}
