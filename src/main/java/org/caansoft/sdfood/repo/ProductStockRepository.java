package org.caansoft.sdfood.repo;


import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.ProductStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {

    @Query(value = "select new ProductStock(ps.product, sum(ps.quantityIn) as sumQuantityIn, "
            + "sum(ps.quantityOut) as sumQuantityOut) from ProductStock ps "
            + "where ps.product = :productId and date(ps.createdOn) <= date(:dateIn)")
    ProductStock findQuantitySum(
            @Param("productId") Product productId,
            @Param("dateIn") Timestamp dateIn);
    
    @Query(value = "select new ProductStock(ps.product,"
    		+ "p.name, "    		
    		+ "sum(case when ps.quantityIn is null then coalesce(ps.quantityIn, 0) else ps.quantityIn end) as sumQuantityIn, "
            + "sum(case when ps.quantityOut is null then coalesce(ps.quantityOut,0)else ps.quantityOut end) as sumQuantityOut) "
            + "from ProductStock ps  "
            + "join Product p "
            + "on p.id = ps.product "         
            + "where (:productName is null or p.name like '%'||:productName||'%' ) "
            + "and(:startDate is null or date(ps.createdOn) >= date(:startDate)) "
			+ "and(:endDate is null or date(ps.createdOn) <= date(:endDate)) "
			+ "and p.flag = :flag "
			+ "group by ps.product " )
    Page<ProductStock> findProductStock(
    		@Param("productName") String productName,
    		@Param("startDate") Timestamp startDate,			
			@Param("endDate") Timestamp endDate,
			@Param("flag") Flag flag,
			Pageable pageable
    		);
    @Query(value="select new ProductStock(max(ps.id)) from ProductStock ps")
    ProductStock findMaxId();
    
}
