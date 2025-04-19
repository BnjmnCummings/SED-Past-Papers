package Q1.original;

import com.dailymail.DailyMail;
import com.dailymail.DailyMailArticle;

import java.util.List;

import static java.util.Comparator.comparing;

public class NewsApp {

  public static void main(String[] args) {
    new NewsApp().latestStories().forEach(a -> System.out.println(a.hline()));
  }

  public List<DailyMailArticle> latestStories() {

    List<DailyMailArticle> articles = DailyMail.getArticles();

    articles.sort(comparing(DailyMailArticle::timestamp).reversed());

    return articles;
  }
}


