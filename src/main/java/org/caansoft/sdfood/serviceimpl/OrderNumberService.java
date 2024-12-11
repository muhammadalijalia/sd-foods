package org.caansoft.sdfood.serviceimpl;

import org.caansoft.sdfood.model.OrderNumber;
import org.caansoft.sdfood.repo.OrderNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrderNumberService {

    @Autowired
    private OrderNumberRepository repo;

    @Transactional
    public OrderNumber generateQuotationNumber(){

        repo.updateQuotationNumber();
        Optional<OrderNumber> orderNumberOptional = repo.findById(1l);

        return orderNumberOptional.get();

    }

    @Transactional
    public OrderNumber generateInvoiceNumber(){

        repo.updateInvoiceNumber();
        Optional<OrderNumber> orderNumberOptional = repo.findById(1l);

        return orderNumberOptional.get();

    }
}
