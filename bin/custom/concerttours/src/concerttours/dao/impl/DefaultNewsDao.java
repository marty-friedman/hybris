package concerttours.dao.impl;

import concerttours.dao.NewsDao;
import concerttours.model.NewsModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component("newsDao")
public class DefaultNewsDao implements NewsDao {
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Override
    public List<NewsModel> getNewsForDay(final Date date) {
        if (date == null)
            return Collections.emptyList();
        final LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        final String theDay = localDate.format(DateTimeFormatter.ofPattern(SQL_DATE_FORMAT));
        final String theNextDay = localDate.plusDays(1).format(DateTimeFormatter.ofPattern(SQL_DATE_FORMAT));
        return flexibleSearchService.<NewsModel>search(
                "SELECT {" + NewsModel.PK + "} FROM {" + NewsModel._TYPECODE + "}" +
                        "WHERE {" + NewsModel.DATE + "} >= DATE \'" + theDay + "\'" +
                        "AND {" + NewsModel.DATE + "} <= DATE \'" + theNextDay + "\'"
        ).getResult();
    }
}
