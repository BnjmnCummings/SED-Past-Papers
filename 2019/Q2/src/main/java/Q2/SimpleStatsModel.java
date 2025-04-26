package Q2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleStatsModel {
  private final List<Integer> numbers = new ArrayList<>();
  private final List<Updatable> observers = new ArrayList<>();
  private int max = 0;
  private double mean = 0;

  /* added for some testing syntactic sugar */
  public SimpleStatsModel(Updatable... displays) {
      this.observers.addAll(Arrays.stream(displays).toList());
  }

  /* End points for adding/removing observers */
  public void addObserver(Updatable obs) {
    observers.add(obs);
  }

  public void removeObserver(Updatable obs) {
    observers.remove(obs);
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
    observers.forEach(obs -> obs.update(this));
  }
}
