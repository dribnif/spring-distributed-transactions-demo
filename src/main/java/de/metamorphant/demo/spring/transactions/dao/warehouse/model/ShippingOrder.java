package de.metamorphant.demo.spring.transactions.dao.warehouse.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ShippingOrder {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String firstnameLastname;

    @Column
    private String streetCityPostcodeCountry;

    @Column
    private String itemsJson;


}
