package de.metamorphant.demo.spring.transactions.service;

import de.metamorphant.demo.spring.transactions.SpringTransactionsApplication;
import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.accounting.repositories.AccountingIncomingOrdersRepository;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.repositories.WarehouseShippingOrdersRepository;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.model.OrderItems;
import de.metamorphant.demo.spring.transactions.service.transformer.OrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.ShippingOrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.TransformationException;
import de.metamorphant.demo.spring.transactions.service.utils.BrokenShippingOrderTransformer;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.nullValue;

@SpringBootTest
@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class OrderProcessorServiceTransactionHandlingTest {

    @TestConfiguration
    @Import(SpringTransactionsApplication.class)
    public static class TestConfig{

        @Bean("shippingOrderTransformer")
        @Primary
        public OrderTransformer<ShippingOrder> shippingOrderTransformer(){
            return new BrokenShippingOrderTransformer();
        }
    }

    @Autowired
    private OrderProcessorService orderProcessorService;

    @Autowired
    private WarehouseShippingOrdersRepository shippingOrdersRepository;

    @Autowired
    private AccountingIncomingOrdersRepository incomingOrdersRepository;


    @Test
    void givenEmptyDatabase_whenCreateOrder_thenDataIsPresentInBothDBs() throws TransformationException {
        //given
        Order order = createOrder();

        //when
        try {
            orderProcessorService.createOrder(order);
        } catch (TransformationException te){
            //ignore exception since we're interesting in the state of the database
        }

        //then
        List<ShippingOrder> createdShippingOrders = shippingOrdersRepository.findAll();
        List<IncomingOrder> createdIncomingOrders = incomingOrdersRepository.findAll();


        assertThat(createdShippingOrders, emptyCollectionOf(ShippingOrder.class));
        assertThat(createdIncomingOrders, emptyCollectionOf(IncomingOrder.class));

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