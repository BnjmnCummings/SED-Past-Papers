package Q2;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * We are interested in testing the communication between model and the controller.
 */
public class ModelViewTest {
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    Updatable mockDisplay = context.mock(Updatable.class);
    SimpleStatsModel model = new SimpleStatsModel(mockDisplay);


    @Test
    public void modelUpdatesWhenANewNumberIsAdded() {
        context.checking(new Expectations() {{
            exactly(1).of(mockDisplay).update(model);
        }});

        model.addNumber(5);
    }


    @Test
    public void modelUpdatesForEachNumberAdded() {
        context.checking(new Expectations() {{
            exactly(3).of(mockDisplay).update(model);
        }});

        model.addNumber(5);
        model.addNumber(4);
        model.addNumber(3);
    }

    @Test
    public void modelUpdatesTheMean() {
        context.checking(new Expectations() {{
            exactly(3).of(mockDisplay).update(model);
        }});

        model.addNumber(9);
        model.addNumber(6);
        model.addNumber(3);
    }
}
