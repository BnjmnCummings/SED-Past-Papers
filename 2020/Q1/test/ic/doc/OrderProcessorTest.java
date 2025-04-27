package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderProcessorTest {

  static final Book DESIGN_PATTERNS_BOOK =
      new Book("Design Patterns", "Gamma, Helm, Johnson and Vlissides", 25.99);
  static final Book LEGACY_CODE_BOOK =
      new Book("Working Effectively with Legacy Code", "Feathers", 29.99);

  static final Customer ALICE = new Customer("Alice Jones");
  static final Customer BOB = new Customer("Bob Smith");

  /**
   * Order Processor needs an order() endpoint which should
   *  - send a request to WareHouse (we'll abstract this to a 'Storage' interface).
   *  - if we've run out of an item we need to buyMore() from a buyer
   *  - otherwise we make a call to the charge() endpoint of a Payment System and a further call to dispatch().
   *  - OrderProcessor is an observer for Buyer. We should expose an endpoint newBooksArrived() which will
   *  check the stock again for any pending orders.
   */

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery(); // 'context' always has to be public because JUnit enforces this
  private PaymentSystem paymentSystem = context.mock(PaymentSystem.class);
  private Stock stock = context.mock(Stock.class);
  private Supplier supplier = context.mock(Supplier.class);
  private OrderProcessor orderProcessor = new OrderProcessor(stock, supplier, paymentSystem);

  /* this test doesn't provide a value for 'stockLevel' so it will default to 0 */
  @Test
  public void orderProcessorShouldCheckStockLevelAfterAnOrder() {
    context.checking(new Expectations() {{
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      ignoring(supplier);
      ignoring(paymentSystem);
    }});

    orderProcessor.order(LEGACY_CODE_BOOK, 1, BOB);
  }

  @Test
  public void orderProcessorWillOrderMoreIfStockIsLowerThanOrderQuantity() {
    context.checking(new Expectations() {{
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(2));
      exactly(1).of(supplier).buyMoreOf(LEGACY_CODE_BOOK);
    }});

    orderProcessor.order(LEGACY_CODE_BOOK, 3, BOB);
  }

  @Test
  public void orderProcessorWillAddFailedOrdersToPendingOrders() {
    context.checking(new Expectations() {{
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(2));
      exactly(1).of(supplier).buyMoreOf(LEGACY_CODE_BOOK);
    }});

    orderProcessor.order(LEGACY_CODE_BOOK, 3, BOB);
    assertEquals(1, orderProcessor.numberOfPendingOrders()); // (expected, actual)
  }

  @Test
  public void orderProcessorWillChargeAndDispatchIfBooksAreInStock() {
    context.checking(new Expectations() {{
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(5));
      exactly(1).of(paymentSystem).charge(LEGACY_CODE_BOOK.price() * 3, BOB);
      exactly(1).of(stock).dispatch(LEGACY_CODE_BOOK, 3, BOB);
    }});

    orderProcessor.order(LEGACY_CODE_BOOK, 3, BOB);
    assertEquals(0, orderProcessor.numberOfPendingOrders());
  }

  @Test
  public void newBooksArrivingWillCheckStockLevelForAnyPendingOrders() {
    context.checking(new Expectations() {{
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(2));
      ignoring(supplier);
      exactly(1).of(stock).checkStockLevel(LEGACY_CODE_BOOK);
      will(returnValue(5));
      exactly(1).of(paymentSystem).charge(LEGACY_CODE_BOOK.price() * 3, BOB);
      exactly(1).of(stock).dispatch(LEGACY_CODE_BOOK, 3, BOB);
    }});

    orderProcessor.order(LEGACY_CODE_BOOK, 3, BOB);
    assertEquals(1, orderProcessor.numberOfPendingOrders()); // (expected, actual)
    orderProcessor.newBooksArrived();
    assertEquals(0, orderProcessor.numberOfPendingOrders());
  }
}
