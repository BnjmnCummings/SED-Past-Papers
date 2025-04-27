package ic.doc;

import com.londonstockexchange.StockMarketDataFeed;
import com.londonstockexchange.TickerSymbol;

public class MarketDataAdapter implements MarketDataService{

    /**
     * This adapter method returns an int so it can be the only class to depend on com.londonstockexchange.
     * Problem: we still need a TickerSymbol object to pass in...
     */
    @Override
    public int currentPriceFor(String stock) {
        return StockMarketDataFeed.getInstance().currentPriceFor(getTickerSymbol(stock)).inPennies();
    }

    /**
     * Solution: use a string representation or a duplicate enum.
     * Now this adapter is the only class that touches com.londonstockexchnge
     */
    private TickerSymbol getTickerSymbol(String stock) {
        return switch (stock) {
            case "AMZN" -> TickerSymbol.AMZN;
            case "APPL" -> TickerSymbol.APPL;
            case "FB" -> TickerSymbol.FB;
            case "GOOG" -> TickerSymbol.GOOG;
            case "MSFT" -> TickerSymbol.MSFT;
            case "NFLX" -> TickerSymbol.NFLX;
            default -> throw new IllegalArgumentException(stock + "is not a recognised Ticker Symbol");
        };
    }
}
