package de.metamorphant.demo.spring.transactions.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class Order {
    private Address invoicingAddress;
    private Address shippingAddress;
    private List<OrderItems> items;
    private ZonedDateTime timeCreated;
}
