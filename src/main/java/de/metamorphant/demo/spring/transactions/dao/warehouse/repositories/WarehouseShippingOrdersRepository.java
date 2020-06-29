package de.metamorphant.demo.spring.transactions.dao.warehouse.repositories;

import de.metamorphant.demo.spring.transactions.dao.warehouse.model.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseShippingOrdersRepository extends JpaRepository<ShippingOrder,Long> {
}
