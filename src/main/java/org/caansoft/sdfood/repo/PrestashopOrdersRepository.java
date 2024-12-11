package org.caansoft.sdfood.repo;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


public interface PrestashopOrdersRepository extends PagingAndSortingRepository<PrestashopOrders, Long>, 
	JpaSpecificationExecutor<PrestashopOrders>, JpaRepository<PrestashopOrders, Long>{
	
//	List<PrestashopOrders> findByDateOrderAddedIsBetween(Timestamp startDate, Timestamp endDate);
	
	@Query(value = "select new PrestashopOrders(count(prestashopOrderId) as totalOrders,"
			+ "sum(orderTotalPriceTaxIncl) as totalSalesAmount) "					
			+ "from PrestashopOrders po"
			+ " where date(dateOrderAdded) between date(:startDate) and date(:endDate)"
			+ " and po.orderCurrentState <> '6'")
	PrestashopOrders findByDateOrderAddedIsBetween(
			@Param("startDate")Timestamp startDate, 
			@Param("endDate")Timestamp endDate);
		
	@Query(value="select prestashop_order_id  from prestashop_orders "
			+ "order by prestashop_order_id desc limit 1", nativeQuery = true)
	Integer findLastRecordId();
	
	@Query(value="select new PrestashopOrders(min(dateOrderAdded) as startDate, max(dateOrderAdded) as endDate) "
			+ "from PrestashopOrders")
	PrestashopOrders findStartandEndDate();
	
	Page<PrestashopOrders> findAll(Pageable pagable);
	
	PrestashopOrders findByPrestashopOrderId(Integer id);
	
	@Transactional
	void deleteByPrestashopOrderId(Integer id);
	
	List<PrestashopOrders> findAllByPrestashopOrderIdIn(List<Integer> ids);
	
	List<PrestashopOrders> findAllByProcessedIsNull();
	
	@Query(value="select distinct(processed) from prestashop_orders", nativeQuery = true)
	List<String> findDistinctProcessed();
	
}
