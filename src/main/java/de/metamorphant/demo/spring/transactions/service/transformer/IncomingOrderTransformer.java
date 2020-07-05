package de.metamorphant.demo.spring.transactions.service.transformer;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrderItems;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import de.metamorphant.demo.spring.transactions.service.model.OrderItems;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IncomingOrderTransformer {
    public IncomingOrder createIncomingOrder(Order order) {
        if (order == null){
            return null;
        }

        Address invoicingAddress = order.getInvoicingAddress();
        List<IncomingOrderItems> orderItems = createOrderItems(order.getItems());

        BigDecimal sumTotal = orderItems.stream()
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, (a, b ) -> a.add(b));

        return IncomingOrder.builder()
                .invoicingFirstName(invoicingAddress.getFirstName())
                .invoicingLastName(invoicingAddress.getLastName())
                .invoicingStreet(invoicingAddress.getStreetNameNumber())
                .invoicingCity(invoicingAddress.getCity())
                .invoicingPostcode(invoicingAddress.getPostCode())
                .invoicingCountry(invoicingAddress.getCountry())
                .orderItems(orderItems)
                .createdDate(ZonedDateTime.now())
                .shippingAddress(String.valueOf(order.getShippingAddress()))
                .total(sumTotal)
                .build();

    }

    private List<IncomingOrderItems> createOrderItems(List<OrderItems> items) {
        if (CollectionUtils.isEmpty(items)){
            return null;
        }
        return items.stream()
                .map(orderItem -> IncomingOrderItems.builder()
                        .description(orderItem.getDescription())
                        .price(orderItem.getPrice())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }


}
