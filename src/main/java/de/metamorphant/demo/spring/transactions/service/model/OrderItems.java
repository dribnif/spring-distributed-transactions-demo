package de.metamorphant.demo.spring.transactions.service.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@ToString
public class OrderItems {
    private String description;
    private int quantity;
    private BigDecimal price;
}
