package ic.doc;

import java.util.ArrayList;
import java.util.List;

public class OrderProcessor {
    private final ArrayList<Order> pendingOrders = new ArrayList<>();
    private Stock stock;
    private Supplier supplier;
    private PaymentSystem paymentSystem;

    public OrderProcessor(
            Stock stock,
            Supplier supplier,
            PaymentSystem paymentSystem
    ) {
        this.stock = stock;
        this.supplier = supplier;
        this.paymentSystem = paymentSystem;
    }

    public void order(Book book, int quantity, Customer customer) {
        if(!inStock(book, quantity, customer)) {
            supplier.buyMoreOf(book);
            pendingOrders.add(new Order(book, quantity, customer));
        }
    }

    /**
     * Helper function which returns true iff the stock has enough of a book to satisfy an order.
     * Calls chargeAndDispatch if successful.
     */
    private boolean inStock(Book book, int quantity, Customer customer) {
        int stockLevel = stock.checkStockLevel(book);

        if(stockLevel >= quantity) {
            chargeAndDispatch(book, quantity, customer);
            return true;
        }

        return false;
    }

    private boolean inStock(Order myOrder) {
        return inStock(myOrder.book, myOrder.quantity, myOrder.customer);
    }

    private void chargeAndDispatch(Book book, int quantity, Customer customer) {
        paymentSystem.charge(getPrice(book, quantity), customer);
        stock.dispatch(book, quantity, customer);
    }

    public void newBooksArrived() {
        pendingOrders.removeIf(this::inStock);
    }

    public int numberOfPendingOrders() {
        return pendingOrders.size();
    }

    private double getPrice(Book book, int quantity) {
        return book.price() * quantity;
    }

    private record Order(Book book, int quantity, Customer customer) {}
}
