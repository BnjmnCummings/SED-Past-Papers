package Q2;

import java.util.ArrayList;

public class CardChecker {
    public ArrayList<Observer> observerList = new ArrayList<>();

    public void addObserver(Observer o) {
        observerList.add(o);
    }

    public void removeObserver(Observer o) {
        observerList.remove(o);
    }

    /**
     * Alerts each observer whenever an invalid card number is entered
     */
    public void validate(String cardNumber) {
        if(cardNumber.length() != 12) {
            for(Observer obs : observerList) {
                obs.alert(cardNumber);
            }
        }
    }
}
