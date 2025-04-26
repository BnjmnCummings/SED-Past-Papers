package Q2;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Display implements Updatable {
    private final JTextField currentMax = new JTextField(11);
    private final JTextField currentMean = new JTextField(11);

    public Display(ActionListener[] listeners) {
        JFrame frame = new JFrame("Simple Stats");
        frame.setSize(250, 350);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Max: value "));
        panel.add(currentMax);
        panel.add(new JLabel("Mean: value "));
        panel.add(currentMean);
        initialiseButtons(panel, listeners);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initialiseButtons(JPanel panel, ActionListener[] listeners) {
        for (int i = 0; i < 12; i++) {
            JButton button = new JButton(String.valueOf(i + 1));
            button.addActionListener(listeners[i]);
            panel.add(button);
        }
    }

  /**
   * Since this is such a simple update, we could maybe get away with just passing in
   * values for mean and max here. But it makes our mock tests weird.
   *
   *    @Override
   *    public void update(int max, double mean) {
   *         currentMax.setText(String.valueOf(max));
   *         currentMean.setText(String.valueOf(mean));
   *   }
   */
  @Override
  public void update(SimpleStatsModel model) {
        currentMax.setText(String.valueOf(model.getMax()));
        currentMean.setText(String.valueOf(model.getMean()));
  }
}
