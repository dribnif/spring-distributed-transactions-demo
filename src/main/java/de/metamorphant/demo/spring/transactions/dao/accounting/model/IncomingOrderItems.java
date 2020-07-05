package de.metamorphant.demo.spring.transactions.dao.accounting.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class IncomingOrderItems {


    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String description;

    @Column
    private float quantity;

    @Column
    private BigDecimal price;
}