package de.metamorphant.demo.spring.transactions.service.transformer;

import de.metamorphant.demo.spring.transactions.service.model.Order;

public interface OrderTransformer<T> {
    public T transformOrder(Order order) throws TransformationException;
}
