package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.OrderNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderNumberRepository extends JpaRepository<OrderNumber, Long> {

    @Modifying
    @Query(value = "update order_number set quotation_number = LAST_INSERT_ID(quotation_number + 1) where id = 1", nativeQuery = true)
    void updateQuotationNumber();

    @Modifying
    @Query(value = "update order_number SET invoice_number = LAST_INSERT_ID(invoice_number + 1) where id = 1", nativeQuery = true)
    void updateInvoiceNumber();

}
