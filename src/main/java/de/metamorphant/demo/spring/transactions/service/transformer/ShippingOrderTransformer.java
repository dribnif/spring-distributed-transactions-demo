package de.metamorphant.demo.spring.transactions.service.transformer;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import de.metamorphant.demo.spring.transactions.service.model.Address;
import de.metamorphant.demo.spring.transactions.service.model.Order;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ShippingOrderTransformer {

    public ShippingOrder createShippingOrder(Order order) throws IOException {
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

        final ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outPutStream, order.getItems());

        return ShippingOrder.builder()
                .firstnameLastname(firstnameLastname)
                .streetCityPostcodeCountry(streetCityPostcodeCountry)
                .itemsJson(new String(outPutStream.toByteArray()))
                .build();
    }
}
