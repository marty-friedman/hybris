package concerttours.service.impl;

import concerttours.dao.NewsDao;
import concerttours.model.NewsModel;
import concerttours.service.NewsService;

import java.util.Date;
import java.util.List;

public class DefaultNewsService implements NewsService {
    private NewsDao newsDao;

    @Override
    public List<NewsModel> getNewsForDay(Date date) {
        return newsDao.getNewsForDay(date);
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }
}
