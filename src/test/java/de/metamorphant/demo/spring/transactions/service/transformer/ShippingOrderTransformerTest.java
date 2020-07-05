package de.metamorphant.demo.spring.transactions.service.transformer;

import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.model.OrderItems;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

class ShippingOrderTransformerTest {

    private ShippingOrderTransformer transformer = new ShippingOrderTransformer();

    @Test
    void given_aNullOrder_when_createShippingOrder_then_nullShippingOrderCreated() throws TransformationException {
        assertThat(transformer.transformOrder(null), nullValue());
    }

    @Test
    void given_aValidOrder_when_createShippingOrder_then_validShippingOrderCreated() throws TransformationException {
        //given
        Order order = createOrder();

        //when
        ShippingOrder shippingOrder = transformer.transformOrder(order);

        //then
        assertThat(shippingOrder, notNullValue());
        assertThat(shippingOrder.getFirstnameLastname(), is("Jeffrey Lebowski"));
        assertThat(shippingOrder.getStreetCityPostcodeCountry(),
                is("606 Venezia Ave, Venice, CA 90291, USA"));
        assertThat(shippingOrder.getItemsJson(),
                is("[{\"description\":\"Bowling ball\"," +
                        "\"quantity\":2.0,\"price\":68.56}," +
                        "{\"description\":\"Bowling Shoes\"," +
                        "\"quantity\":1.0,\"price\":145.99}]"));
    }

    private Order createOrder() {
        Address address = Address.builder().firstName("Jeffrey")
                .lastName("Lebowski")
                .streetNameNumber("606 Venezia Ave")
                .city("Venice")
                .postCode("CA 90291")
                .country("USA").build();

        List<OrderItems> items = new ArrayList<>();
        items.add(OrderItems.builder()
                .description("Bowling ball")
                .quantity(2)
                .price(new BigDecimal("68.56")).build());
        items.add(OrderItems.builder()
                .description("Bowling Shoes")
                .quantity(1)
                .price(new BigDecimal("145.99")).build());

        return Order.builder().invoicingAddress(address)
                .shippingAddress(address)
                .timeCreated(ZonedDateTime.now())
                .items(items)
                .build();
    }

}