package Q1.solutions;

import com.dailymail.DailyMail;
import com.dailymail.DailyMailArticle;

import java.util.List;

public class NewsSourceAdapter implements NewsSource {

    @Override
    public List<Article> getArticles() {
        return DailyMail.getArticles().stream().map( dailyMailArticle ->
                new Article(
                        dailyMailArticle.timestamp(),
                        dailyMailArticle.author(),
                        dailyMailArticle.hline(),
                        dailyMailArticle.body()
                )
        ).toList();
    }
}
