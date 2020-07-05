package de.metamorphant.demo.spring.transactions.service.utils;

import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.transformer.OrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.ShippingOrderTransformer;
import de.metamorphant.demo.spring.transactions.service.transformer.TransformationException;
import org.springframework.stereotype.Component;

@Component
public class BrokenShippingOrderTransformer implements OrderTransformer<ShippingOrder> {

    @Override
    public ShippingOrder transformOrder(Order order) throws TransformationException {
        throw new TransformationException("I am evil! I shan't transform shite for you!");
    }
}
