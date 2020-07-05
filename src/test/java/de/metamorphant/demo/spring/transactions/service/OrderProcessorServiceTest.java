package de.metamorphant.demo.spring.transactions.service;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.accounting.repositories.AccountingIncomingOrdersRepository;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.repositories.WarehouseShippingOrdersRepository;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.model.OrderItems;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class OrderProcessorServiceTest {

    @Autowired
    private OrderProcessorService orderProcessorService;

    @Autowired
    private WarehouseShippingOrdersRepository shippingOrdersRepository;

    @Autowired
    private AccountingIncomingOrdersRepository incomingOrdersRepository;


    @Test
    void givenEmptyDatabase_whenCreateOrder_thenDataIsPresentInBothDBs() throws IOException {
        //given
        Order order = createOrder();

        //when
        orderProcessorService.createOrder(order);

        //then
        List<ShippingOrder> createdShippingOrders = shippingOrdersRepository.findAll();
        List<IncomingOrder> createdIncomingOrders = incomingOrdersRepository.findAll();


        assertThat(createdShippingOrders, notNullValue());
        assertThat(createdShippingOrders.size(), is(1));
        ShippingOrder actualShippingOrder = createdShippingOrders.get(0);
        //merely spot-checking some properties of the created objects
        assertThat(actualShippingOrder, notNullValue());
        assertThat(actualShippingOrder.getFirstnameLastname(), is("Jeffrey Lebowski"));
        assertThat(actualShippingOrder.getStreetCityPostcodeCountry(),
                is("606 Venezia Ave, Venice, CA 90291, USA"));
        assertThat(actualShippingOrder.getItemsJson(), is("[{\"description\":\"Bowling ball\"," +
                "\"quantity\":2.0,\"price\":68.56}," +
                "{\"description\":\"Bowling Shoes\"," +
                "\"quantity\":1.0,\"price\":145.99}]"));


        //merely spot-checking some properties of the created objects
        assertThat(createdIncomingOrders, notNullValue());
        assertThat(createdIncomingOrders.size(), is(1));
        IncomingOrder actualIncomingOrder = createdIncomingOrders.get(0);
        assertThat(actualIncomingOrder, notNullValue());
        assertThat(actualIncomingOrder.getTotal(), Matchers.is(new BigDecimal("214.55")));
        assertThat(actualIncomingOrder.getOrderItems(), notNullValue());
        assertThat(actualIncomingOrder.getOrderItems().size(), is(2));
        assertThat(actualIncomingOrder.getOrderItems().get(0), notNullValue());
        assertThat(actualIncomingOrder.getOrderItems().get(0).getDescription(), is("Bowling ball"));
        assertThat(actualIncomingOrder.getOrderItems().get(1).getDescription(), is("Bowling Shoes"));

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