package de.metamorphant.demo.spring.transactions.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class OrderProcessorService {



    public void createOrder(Order order) throws IOException {
        IncomingOrder incomingOrder = createIncomingOrder(order);
        ShippingOrder shippingOrder = createShippingOrder(order);
    }

    //protected to enable quick testability
    protected ShippingOrder createShippingOrder(Order order) throws IOException {
        if (order == null){
            return null;
        }

        Address shippingAddress = order.getShippingAddress();
        String firstnameLastname = shippingAddress.getFirstName()
                + " " + shippingAddress.getLastName();

        StringBuilder builder = new StringBuilder();

        String streetCityPostcodeCountry = builder.append(shippingAddress.getStreetNameNumber()).append(", ")
                .append(shippingAddress.getCity()).append(", ")
                .append(shippingAddress.getPostCode()).append(", ")
                .append(shippingAddress.getCountry()).toString();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, order.getItems());

        final byte[] data = out.toByteArray();
        System.out.println(new String(data));

        return ShippingOrder.builder()
                .firstnameLastname(firstnameLastname)
                .streetCityPostcodeCountry(streetCityPostcodeCountry)
                .itemsJson(new String(data))
                .build();
    }
    //protected to enable quick testability
    protected IncomingOrder createIncomingOrder(Order order) {
        return null;
    }
}
