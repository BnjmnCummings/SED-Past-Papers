package Q1.solutions;

import java.util.List;

public record Article(long time, String author, String headline, List<String> body) {}
