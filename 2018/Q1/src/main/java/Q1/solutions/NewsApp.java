package Q1.solutions;

import java.util.*;

import static java.util.Comparator.comparing;

public class NewsApp {

  private final NewsSource source;

  private NewsApp(NewsSource source) {
    this.source = source;
  }

  public static void main(String[] args) {
    new NewsApp(new NewsSourceAdapter()).latestStories().forEach(a -> System.out.println(a.headline()));
  }

  public List<Article> latestStories() {

    List<Article> articles = source.getArticles();

    articles.sort(comparing(Article::time).reversed());

    return articles;
  }
}
