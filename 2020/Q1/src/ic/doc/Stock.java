package ic.doc;

public interface Stock {
    int checkStockLevel(Book book);

    void dispatch(Book book, int quantity, Customer customer);
}
