package org.caansoft.sdfood.repo;

import java.sql.Timestamp;
import java.util.List;

import org.caansoft.sdfood.prestashop.model.PrestashopOrderProducts;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PrestashopOrderProductsRepository extends PagingAndSortingRepository<PrestashopOrderProducts, Long>,
	JpaRepository<PrestashopOrderProducts, Long>{
	
	@Query(value = "select new PrestashopOrderProducts (pop.productId , pop.productName, "
			+ "sum(pop.productQuantity) as productTotalQuantity, "
			+ "sum(pop.producdPriceTaxIncl * pop.productQuantity) as productTotalSales,\r\n "
			+ "sum(pop.productQuantity * pop.productPrice) as productTotalSalesTaxExcl, "
			+ "(sum(pop.producdPriceTaxIncl * pop.productQuantity) - sum(pop.productQuantity * pop.productPrice)) "
			+ "as taxValue, sum(pop.productCostPrice * pop.productQuantity) as totalCostPrice) "
			+ " from PrestashopOrders o \r\n"			
			+ "inner join PrestashopOrderProducts pop on o.prestashopOrderId = pop.order \r\n"
			+ "where date(o.dateOrderAdded) between date(:startDate) and date(:endDate) "
			+ "and o.orderCurrentState in (31,32) and pop.productName like %:productName% "
			+ "group by pop.productName, pop.productId order by productTotalQuantity desc"	)
	Page<PrestashopOrderProducts> findProductsBySales(
			@Param("startDate")Timestamp startDate,			
			@Param("endDate")Timestamp endDate, 
			@Param("productName") String productName,
			Pageable pageable
			);
	
	@Query(value = "select distinct(product_name) from prestashop_order_products", nativeQuery = true)
	List<String> findDistinctProductName();
	
	Page<PrestashopOrderProducts> findAllByOrder(PrestashopOrders order, Pageable pageable);

	
}
