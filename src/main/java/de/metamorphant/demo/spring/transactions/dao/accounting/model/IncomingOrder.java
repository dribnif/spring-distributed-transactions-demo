package de.metamorphant.demo.spring.transactions.dao.accounting.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class IncomingOrder {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String invoicingFirstName;

    @Column
    private String invoicingLastName;

    @Column
    private String invoicingStreet;

    @Column
    private String invoicingCity;

    @Column
    private String invoicingCountry;

    @Column
    private String invoicingPostcode;

    @Column
    private String shippingAddress;

    @Column
    private BigDecimal total;

    @Column
    private ZonedDateTime createdDate;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<IncomingOrderItems> orderItems;

}
