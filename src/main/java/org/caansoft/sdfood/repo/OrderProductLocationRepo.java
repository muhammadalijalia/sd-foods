package org.caansoft.sdfood.repo;

import java.sql.Timestamp;
import java.util.List;

import org.caansoft.sdfood.model.OrderProduct;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OrderProductLocationRepo extends PagingAndSortingRepository<OrderProductLocation, Long>,
		JpaRepository<OrderProductLocation, Long>,
		JpaSpecificationExecutor<OrderProductLocation>{

	List<OrderProductLocation> findByProductIdAndExpiry(Long id, Timestamp expiry);
	
	List<OrderProductLocation> findByProductId(Long id);
	
	List<OrderProductLocation> findAll();
	
	@Query(value = "select new OrderProductLocation ("
			+ "p.id as queryProductId, "
			+ "p2.id as vendorId, "
			+ "p.name as productName, "
			+ "p2.name as vendorName, "
			+ "sum(case when op.quantity is null then coalesce(op.quantity,0) "
			+ "else op.quantity end) as totalOrderedPallets, "
			+ "sum(case when opl.receivedPallets is null then coalesce(opl.receivedPallets, 0) \r\n"
			+ "else opl.receivedPallets end) as totalReceivedPallets, "
			+ "sum(case when op.numberOfUnits is null then coalesce(op.numberOfUnits,0) \r\n"
			+ "else op.numberOfUnits end) as totalOrderedNumberOfunits, "
			+ "sum(case when opl.receivedNumberofUnits is null then opl.receivedQuantity else opl.receivedNumberofUnits end) "
			+ "as totalReceivedNumberOfUnits, "
			+ "sum(case \r\n"
			+ "	when opl.damagedQuantity is null \r\n"
			+ "		then \r\n"
			+ "		(case when opl.damageNumberOfUnits is null then coalesce(opl.damageNumberOfUnits,0)\r\n"
			+ "			else opl.damageNumberOfUnits end)\r\n"
			+ "		else opl.damagedQuantity end)"
			+ "as totalDamagedQuantity) "
			+ " from OrderProductLocation opl "
			+ "right join OrderProduct op "
			+ "on op.id = opl.orderProducts "
			+ "join Order o "
			+ "on o.id = op.order "
			+ "join Product p "
			+ "on p.id = op.product "
			+ "join Partner p2 "
			+ "on p2.id = o.partners "
			+ "where o.flag <> 'DELETED' "
			+ "and o.status in ('CONFIRMED','RECEIVED', 'STOCKED','PUBLISHED') " 			
			+ "and(:productName is null or p.name like '%'||:productName||'%') "
			+ "and(:vendorName is null or p2.name like '%'||:vendorName||'%') "
			+ "and(:startDate is null or date(o.createdOn) >= date(:startDate)) "
			+ "and(:endDate is null or date(o.createdOn) <= date(:endDate)) "
			+ "group by op.product, p2.id order by op.product ") 
	public Page<OrderProductLocation> findByOrderProductLocation(
			@Param("startDate")Timestamp startDate,			
			@Param("endDate")Timestamp endDate, 
			@Param ("productName") String productName,
			@Param ("vendorName") String vendorName,
			Pageable pageable
			);
	List<OrderProductLocation> findAllByOrderProductsIn(List<OrderProduct> ids);
}
