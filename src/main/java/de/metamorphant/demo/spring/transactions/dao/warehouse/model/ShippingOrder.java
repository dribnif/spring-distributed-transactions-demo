package de.metamorphant.demo.spring.transactions.dao.warehouse.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
public class ShippingOrder {

    @Id
    private long id;

    @Column
    private String firstnameLastname;

    @Column
    private String streetCityPostcodeCountry;

    @Column
    private String itemsJson;


}
