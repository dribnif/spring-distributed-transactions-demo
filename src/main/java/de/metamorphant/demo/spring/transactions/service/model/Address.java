package de.metamorphant.demo.spring.transactions.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Address {
    private String firstName;
    private String lastName;
    private String streetNameNumber;
    private String city;
    private String postCode;
    private String country;

}
