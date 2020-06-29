package de.metamorphant.demo.spring.transactions.dao.accounting.repositories;

import de.metamorphant.demo.spring.transactions.dao.accounting.model.IncomingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountingIncomingOrdersRepository extends JpaRepository<IncomingOrder, Long> {
}
