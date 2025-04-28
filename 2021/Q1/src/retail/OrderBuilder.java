package retail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderBuilder {
    private static final int MAX_SMALL_ORDER = 3;

    private final List<Product> items = new ArrayList<>();
    private final CreditCardDetails creditCardDetails;
    private final Address billingAddress;
    private final Courier courier;

    private OrderSize orderSize = OrderSize.SMALL;
    private boolean giftWrap = false;
    private BigDecimal discount = BigDecimal.valueOf(0);
    private Address shippingAddress;

    private OrderBuilder(
            Address billingAddress,
            CreditCardDetails creditCardDetails,
            Courier courier
    ) {
        this.billingAddress = billingAddress;
        this.shippingAddress = billingAddress;
        this.creditCardDetails = creditCardDetails;
        this.courier = courier;
    }

    public static OrderBuilder anOrder(
            Address shippingAddress,
            CreditCardDetails creditCardDetails,
            Courier courier
    ) {
        return new OrderBuilder(shippingAddress, creditCardDetails, courier);
    }

    public OrderBuilder addShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public OrderBuilder addItems(List<Product> items) {
        this.items.addAll(items);
        if(this.items.size() > MAX_SMALL_ORDER) {
            orderSize = OrderSize.BULK;
        }
        return this;
    }

    public OrderBuilder addItems(Product... items) {
        return this.addItems(Arrays.stream(items).toList());
    }

    public OrderBuilder addGiftWrap() {
        giftWrap = true;
        return this;
    }

    public OrderBuilder addDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public Order build() {
        return switch (orderSize) {
            case SMALL -> new SmallOrder(
                    items,
                    creditCardDetails,
                    billingAddress,
                    shippingAddress,
                    courier,
                    giftWrap
            );
            case BULK -> new BulkOrder(
                    items,
                    creditCardDetails,
                    billingAddress,
                    shippingAddress,
                    courier,
                    discount
            );
        };
    }

    private enum OrderSize {
        SMALL,
        BULK,
    }
}
