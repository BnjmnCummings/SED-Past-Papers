package Q1.solutions;

public class LaTeXFormatter implements Formatter{
    @Override
    public String formatHeader() {
        return "\\begin{itemize}";
    }

    @Override
    public String formatItem(String item) {
        return "  \\item " + item;
    }

    @Override
    public String formatFooter() {
        return "\\end{itemize}";
    }
}
