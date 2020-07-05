package de.metamorphant.demo.spring.transactions.service;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.accounting.repositories.AccountingIncomingOrdersRepository;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.repositories.WarehouseShippingOrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionalOrderPersistenceService {

    @Autowired
    private WarehouseShippingOrdersRepository shippingOrdersRepository;

    @Autowired
    private AccountingIncomingOrdersRepository incomingOrdersRepository;

    public void persistOrders(IncomingOrder incomingOrder, ShippingOrder shippingOrder) {

        incomingOrdersRepository.save(incomingOrder);
        shippingOrdersRepository.save(shippingOrder);

    }
}
