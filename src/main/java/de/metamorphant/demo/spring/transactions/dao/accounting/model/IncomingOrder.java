package de.metamorphant.demo.spring.transactions.dao.accounting.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class IncomingOrder {

    @Id
    private long id;
}
