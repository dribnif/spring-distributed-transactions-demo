package de.metamorphant.demo.spring.transactions.service;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.transformer.IncomingOrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.ShippingOrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OrderProcessorService {

    @Autowired
    private IncomingOrderTransformer incomingOrderTransformer;

    @Autowired
    private ShippingOrderTransformer shippingOrderTransformer;

    @Autowired
    private TransactionalOrderPersistenceService orderPersistenceService;

    public void createOrder(Order order) throws IOException {
        IncomingOrder incomingOrder = incomingOrderTransformer.createIncomingOrder(order);
        ShippingOrder shippingOrder = shippingOrderTransformer.createShippingOrder(order);

        orderPersistenceService.persistOrders(incomingOrder,shippingOrder);

    }

}
