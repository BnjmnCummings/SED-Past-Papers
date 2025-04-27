package ic.doc;

import com.londonstockexchange.TickerSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AlgoTrader {
  private final Map<String, Integer> lastPrices = new HashMap<>();
  private final MarketDataService market;
  private final Broker broker;
  private final List<String> stocksToWatch;

  /**
   * Passing concrete classes into the constructor is a nice way to do dependency inversion.
   * The VarArgs stocksToWatch probably isn't necessary.
   */
  public AlgoTrader(MarketDataService market, Broker broker, String... stocksToWatch) {
    this.market = market;
    this.broker = broker;
    this.stocksToWatch = List.of(stocksToWatch);
  }

  public void trade() {

    for (String stock : stocksToWatch) {

      int price = market.currentPriceFor(stock);

      if (isRising(stock, price)) {
        broker.buy(stock);
      }

      if (isFalling(stock, price)) {
        broker.sell(stock);
      }

      lastPrices.put(stock, price);
    }
  }

  private boolean isFalling(String stock, int price) {
    int lastPrice = lastPrices.containsKey(stock) ? lastPrices.get(stock) : 0;
    return price < lastPrice;
  }

  private boolean isRising(String stock, int price) {
    int lastPrice = lastPrices.containsKey(stock) ? lastPrices.get(stock) : Integer.MAX_VALUE;
    return price > lastPrice;
  }

  public static void main(String[] args) {
    new AlgoTrader(new MarketDataAdapter(), new SimpleBroker(), "GOOG", "AMZN", "APPL").start();
  }

  // code below here is not important for the exam

  private void logPrices(TickerSymbol stock, int price, int lastPrice) {
    System.out.println(
        String.format("%s used to be %s, now %s ", stock, lastPrice, price));
  }

  private void start() {

    // run trade() every minute

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    executorService.scheduleAtFixedRate(this::trade, 0, 60, TimeUnit.SECONDS);
  }
}
