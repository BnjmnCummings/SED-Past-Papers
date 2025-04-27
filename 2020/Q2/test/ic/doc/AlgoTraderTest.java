package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AlgoTraderTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    Broker broker = context.mock(Broker.class);
    MarketDataService market = context.mock(MarketDataService.class);
    AlgoTrader trader = new AlgoTrader(market, broker, "APPL");

    /**
     * We can set up the initial trade with a @before method.
     */
    @Before
    public void init() { //before methods have to be public
        context.checking(new Expectations() {{
            exactly(1).of(market).currentPriceFor("APPL");
            will(returnValue(10));
        }});

        trader.trade();
    }

    @Test
    public void traderBuysWhenStockGoesUp() {
        context.checking(new Expectations() {{
            exactly(1).of(market).currentPriceFor("APPL");
            will(returnValue(15));
            exactly(1).of(broker).buy("APPL");
        }});

        trader.trade();
    }

    @Test
    public void traderSellsWhenStockGoesDown() {
        context.checking(new Expectations() {{
            exactly(1).of(market).currentPriceFor("APPL");
            will(returnValue(5));
            exactly(1).of(broker).sell("APPL");
        }});

        trader.trade();
    }
}
