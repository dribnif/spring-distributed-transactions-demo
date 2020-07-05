package de.metamorphant.demo.spring.transactions.service.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ShippingOrderTransformer implements OrderTransformer<ShippingOrder>{

    public ShippingOrder transformOrder(Order order) throws TransformationException {
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

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        try {

            mapper.writeValue(outputStream, order.getItems());
        } catch (IOException ioe){
            throw new TransformationException("Failed to serialize items to JSON", ioe);
        }

        return ShippingOrder.builder()
                .firstnameLastname(firstnameLastname)
                .streetCityPostcodeCountry(streetCityPostcodeCountry)
                .itemsJson(new String(outputStream.toByteArray()))
                .build();
    }
}
