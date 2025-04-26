package Q2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Changing the constructor to no longer require a display allows us to do proper unit testing.
 */
public class SimpleStatsModelTest {
    @Test
    public void modelSetsTheFirstInsertedNumberToMeanAndMax() {
        SimpleStatsModel model = new SimpleStatsModel();
        model.addNumber(5);

        assertEquals(model.getMax(), 5);
        assertEquals(model.getMean(), 5.0, 0.1);
    }

    @Test
    public void modelRecalculatesTheMaxCorrectly() {
        SimpleStatsModel model = new SimpleStatsModel();
        model.addNumber(5);
        model.addNumber(4);
        model.addNumber(3);

        assertEquals(model.getMax(), 5);
    }

    @Test
    public void modelRecalculatesTheMeanCorrectly() {
        SimpleStatsModel model = new SimpleStatsModel();
        model.addNumber(9);
        model.addNumber(6);
        model.addNumber(3);

        assertEquals(model.getMean(), 6.0, 0.1);
    }
}
