package concerttours.jobs;

import concerttours.model.NewsModel;
import concerttours.service.NewsService;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.mail.MailUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SendNewsJob extends AbstractJobPerformable<CronJobModel> {
    private static final Logger LOGGER = Logger.getLogger(SendNewsJob.class);

    private NewsService newsService;

    @Override
    public PerformResult perform(final CronJobModel cronJobModel) {
        final List<NewsModel> newsOfTheDay = newsService.getNewsForDay(new Date());
        if (newsOfTheDay.isEmpty()) {
            LOGGER.info("No news for today, nothing to send");
            return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
        }
        final AtomicInteger index = new AtomicInteger(1);
        final String message = newsOfTheDay.stream().reduce(new StringBuilder(),
                (sb, model) -> sb.append(index.getAndIncrement()).append(". ").append(model.getHeadline())
                        .append("\n").append(model.getContent()).append("\n\n"),
                StringBuilder::append).toString();
        try {
            sendEmail(message);
        } catch (EmailException e) {
            LOGGER.error("Cannot send email", e);
            return new PerformResult(CronJobResult.ERROR, CronJobStatus.FINISHED);
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void sendEmail(final String message) throws EmailException {
        final String subject = "Daily news summary";
        final Email email = MailUtils.getPreConfiguredEmail();
        email.addTo(Config.getString("news_summary_mailing_address", null));
        email.setSubject(subject);
        email.setMsg(message);
        email.setTLS(true);
        email.send();
        LOGGER.info(subject);
        LOGGER.info(message);
    }

    public void setNewsService(final NewsService newsService) {
        this.newsService = newsService;
    }
}
