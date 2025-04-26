package Q2;

import java.awt.event.ActionListener;

/**
 * The Controller class needs information about the numbers on the button
 * in order to make the correct update to model.
 */
class Controller  {
    private final SimpleStatsModel model;

    public Controller(SimpleStatsModel model) {
        this.model = model;
    }
    public ActionListener addNumberButton(int value) {
        return e -> model.addNumber(value);
    }
}
