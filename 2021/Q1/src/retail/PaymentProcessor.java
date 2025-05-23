package retail;

import java.math.BigDecimal;

public interface PaymentProcessor {
    void charge(BigDecimal amount, CreditCardDetails account, Address billingAddress);
}