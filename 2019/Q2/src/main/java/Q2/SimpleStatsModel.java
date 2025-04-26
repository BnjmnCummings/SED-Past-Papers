package Q2;

import java.security.URIParameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;

public class SimpleStatsModel {
  private final List<Integer> numbers = new ArrayList<>();
  private final List<Updatable> displays = new ArrayList<>();
  private int max = 0;
  private double mean = 0;

  /* added for some testing syntactic sugar */
  public SimpleStatsModel(Updatable... displays) {
      this.displays.addAll(Arrays.stream(displays).toList());
  }

  /* End points for adding/removing observers */
  public void addDisplay(Updatable obs) {
    displays.add(obs);
  }

  public void removeDisplay(Updatable obs) {
    displays.remove(obs);
  }

  /* End points for getting and setting the data */
  public int getMax() {
    return max;
  }

  public double getMean() {
    return mean;
  }

  public void addNumber(int num) {
    numbers.add(num);
    updateMean();
    updateMax(num);
    updateDisplays();
  }

  private void updateMax(int newMax) {
    if(newMax > max) {
      max = newMax;
    }
  }

  private void updateMean() {
    mean = numbers.stream().mapToInt(val -> val).average().orElse(0.0);
  }

  private void updateDisplays() {
    displays.forEach(obs -> obs.update(this));
  }
}
