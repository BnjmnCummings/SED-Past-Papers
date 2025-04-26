package Q2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class SimpleStatsApp {
    private final SimpleStatsModel model;

    public SimpleStatsApp() {
        Controller[] numberControllers = new Controller[12];
        Arrays.setAll(numberControllers, Controller::new);

        Display display = new Display(numberControllers);
        model = new SimpleStatsModel(display);
    }

    /**
     * The Controller class needs information about the numbers on the button
     * in order to make the correct
     */
    class Controller implements ActionListener {
        final int value;
        public Controller(int value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            model.addNumber(value);
        }
    }

    public static void main(String[] args) {
        new SimpleStatsApp();
    }
}
