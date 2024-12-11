package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.Order;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    Page<Order> findAll(Pageable pageable);
}
