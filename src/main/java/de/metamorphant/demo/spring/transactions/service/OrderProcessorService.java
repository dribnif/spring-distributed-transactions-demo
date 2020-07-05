package de.metamorphant.demo.spring.transactions.service;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.accounting.repositories.AccountingIncomingOrdersRepository;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.repositories.WarehouseShippingOrdersRepository;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.transformer.IncomingOrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.OrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.ShippingOrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.TransformationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@EnableTransactionManagement
public class OrderProcessorService {

    @Autowired
    private OrderTransformer<IncomingOrder> incomingOrderTransformer;

    @Autowired
    private OrderTransformer<ShippingOrder> shippingOrderTransformer;

    @Autowired
    private WarehouseShippingOrdersRepository shippingOrdersRepository;

    @Autowired
    private AccountingIncomingOrdersRepository incomingOrdersRepository;

    @Transactional(value = "chainedTransactionManager", rollbackFor = {Exception.class}, isolation = Isolation.READ_UNCOMMITTED)
    public void createOrder(Order order) throws TransformationException {
        IncomingOrder incomingOrder = incomingOrderTransformer.transformOrder(order);
        incomingOrdersRepository.save(incomingOrder);

        ShippingOrder shippingOrder = shippingOrderTransformer.transformOrder(order);
        shippingOrdersRepository.save(shippingOrder);

    }

}
