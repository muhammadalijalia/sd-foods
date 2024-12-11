package org.caansoft.sdfood.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrderNumber {

    @Id
    private Long id;
    private Integer quotationNumber;
    private Integer invoiceNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(Integer quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
