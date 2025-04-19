package Q1.solutions;

public class HtmlFormatter implements Formatter {
    @Override
    public String formatHeader() {
        return "<ul>";
    }

    @Override
    public String formatItem(String item) {
        return "  <li>" + item + "</li>";
    }

    @Override
    public String formatFooter() {
        return "</ul>";
    }
}
