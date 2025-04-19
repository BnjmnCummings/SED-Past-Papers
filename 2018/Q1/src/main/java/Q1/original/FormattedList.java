package Q1.original;

import Q1.solutions.Formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FormattedList {

  private List<String> content = new ArrayList<>();

  public FormattedList(String... items) {
    content.addAll(Arrays.asList(items));
  }

  public void add(String item) {
    content.add(item);
  }

  public void print() {
    System.out.println(formatHeader());
    for (String item : content) {
      System.out.println(formatItem(item));
    }
    System.out.println(formatFooter());
  }

  abstract String formatHeader();

  abstract String formatItem(String item);

  abstract String formatFooter();
}

