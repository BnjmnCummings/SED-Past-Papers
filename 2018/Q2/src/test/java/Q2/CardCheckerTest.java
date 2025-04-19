package Q2;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CardCheckerTest {
  // implement your tests here

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    Observer obs1 = context.mock(Observer.class, "obs1");
    Observer obs2 = context.mock(Observer.class, "obs2");

    @Test
    public void addObserversToList() {
        CardChecker checker = new CardChecker();
        checker.addObserver(obs1);

        assertEquals(1, checker.observerList.size());
    }

    @Test
    public void removeObserversFromList() {
        CardChecker checker = new CardChecker();
        checker.addObserver(obs1);
        assertEquals(1, checker.observerList.size());
        checker.removeObserver(obs1);
        assertEquals(0, checker.observerList.size());
    }

    @Test
    public void invalidNumberCallsObserver() {
        CardChecker checker = new CardChecker();
        checker.addObserver(obs1);

        /*
         * Notes on this 'double brace' syntax: https://www.baeldung.com/java-double-brace-initialization
         */
        context.checking( new Expectations() {{
            exactly(1).of(obs1).alert("0");
        }});

        checker.validate("0");
    }

    @Test
    public void validNumber() {
        CardChecker checker = new CardChecker();
        checker.addObserver(obs1);

        context.checking( new Expectations() {});

        checker.validate("111111111111");
    }

    @Test
    public void invalidNumberCallsEveryObserver() {
        CardChecker checker = new CardChecker();
        checker.addObserver(obs1);
        checker.addObserver(obs2);

        context.checking( new Expectations() {{
            exactly(1).of(obs1).alert("0");
            exactly(1).of(obs2).alert("0");
        }});

        checker.validate("0");
    }
}
