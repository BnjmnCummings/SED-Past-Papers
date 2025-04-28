package retail;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static retail.OrderBuilder.anOrder;

public class OrderTest {
    private static final Address TEST_ADDRESS =  new Address("180 Queens Gate, London, SW7 2AZ");
    private static final CreditCardDetails TEST_CARD_DETAILS = new CreditCardDetails("1234123412341234", 111);
    private static final Product TEST_PRODUCT =  new Product("One Book", new BigDecimal("10.00"));
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();
    PaymentProcessor mockProcessor = context.mock(PaymentProcessor.class);
    Courier mockCourier = context.mock(Courier.class);

    @Test
    public void smallOrdersChargedCourierCharge() {
        context.checking(new Expectations() {{
            exactly(1).of(mockCourier).deliveryCharge();
            will(returnValue(new BigDecimal(100)));
            ignoring(mockProcessor);
            ignoring(mockCourier);
        }});

        Order order = anOrder(
                TEST_ADDRESS,
                TEST_CARD_DETAILS,
                mockCourier
        ).addItems(
                TEST_PRODUCT
        ).build();

        order.process(mockProcessor);

    }

    @Test
    public void bulkOrdersDoNotInvokeCourierCharge() {
        context.checking(new Expectations() {{
            exactly(0).of(mockCourier).deliveryCharge();
            ignoring(mockProcessor);
            ignoring(mockCourier);
        }});

        Order order = anOrder(
                new Address("180 Queens Gate, London, SW7 2AZ"),
                new CreditCardDetails("1234123412341234", 111),
                mockCourier
        ).addItems(List.of(
                TEST_PRODUCT,
                TEST_PRODUCT,
                TEST_PRODUCT,
                TEST_PRODUCT,
                TEST_PRODUCT
        )).build();

        order.process(mockProcessor);

    }

}
