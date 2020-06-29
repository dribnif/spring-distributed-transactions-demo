package de.metamorphant.demo.spring.transactions.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Address {
    private String firstName;
    private String lastName;
    private String streetNameNumber;
    private String city;
    private String postCode;
    private String country;

}
