package concerttours.service.impl;

import concerttours.dao.NewsDao;
import concerttours.model.NewsModel;
import concerttours.service.NewsService;

import java.util.Date;
import java.util.List;

public class DefaultNewsService implements NewsService {
    private NewsDao newsDao;

    @Override
    public List<NewsModel> getNewsForDay(final Date date) {
        return newsDao.getNewsForDay(date);
    }

    public void setNewsDao(final NewsDao newsDao) {
        this.newsDao = newsDao;
    }
}
