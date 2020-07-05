package de.metamorphant.demo.spring.transactions.service.transformer;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.model.OrderItems;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class IncomingOrderTransformerTest {

    private IncomingOrderTransformer transformer = new IncomingOrderTransformer();

    @Test
    void given_aNullOrder_when_createIncomingOrder_then_nullShippingOrderCreated() throws IOException {
        assertThat(transformer.transformOrder(null), nullValue());
    }

    @Test
    void given_aValidOrder_when_createIncomingOrder_then_validIncomingOrderCreated() throws IOException {
        //given
        Order order = createOrder();

        //when
        IncomingOrder incomingOrder = transformer.transformOrder(order);

        //then
        assertThat(incomingOrder, notNullValue());
        assertThat(incomingOrder.getInvoicingFirstName(), is("Jeffrey"));
        assertThat(incomingOrder.getInvoicingLastName(), is("Lebowski"));
        assertThat(incomingOrder.getInvoicingStreet(), is("606 Venezia Ave"));
        assertThat(incomingOrder.getInvoicingCity(), is("Venice"));
        assertThat(incomingOrder.getInvoicingPostcode(), is("CA 90291"));
        assertThat(incomingOrder.getInvoicingCountry(), is("USA"));
        assertThat(incomingOrder.getTotal(), is(new BigDecimal("214.55")));

        assertThat(incomingOrder.getOrderItems(), notNullValue());
        assertThat(incomingOrder.getOrderItems().get(0), notNullValue());
        assertThat(incomingOrder.getOrderItems().get(0).getDescription(), is("Bowling ball"));
        assertThat(incomingOrder.getOrderItems().get(0).getQuantity(), is(2.0F));
        assertThat(incomingOrder.getOrderItems().get(0).getPrice(), is(new BigDecimal("68.56")));
        assertThat(incomingOrder.getOrderItems().get(1), notNullValue());
        assertThat(incomingOrder.getOrderItems().get(1).getDescription(), is("Bowling Shoes"));
        assertThat(incomingOrder.getOrderItems().get(1).getQuantity(), is(1.0F));
        assertThat(incomingOrder.getOrderItems().get(1).getPrice(), is(new BigDecimal("145.99")));
        assertThat(incomingOrder.getShippingAddress(), is("Address(firstName=Jeffrey, lastName=Lebowski, " +
                "streetNameNumber=606 Venezia Ave, city=Venice, postCode=CA 90291, country=USA)"));

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