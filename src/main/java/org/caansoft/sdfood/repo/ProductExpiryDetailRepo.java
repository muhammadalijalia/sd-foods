package org.caansoft.sdfood.repo;
import java.util.List;

import javax.transaction.Transactional;
import org.caansoft.sdfood.prestashop.model.ProductExpiryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ProductExpiryDetailRepo extends JpaRepository<ProductExpiryDetail, Long> {
	
	List<ProductExpiryDetail> findByOrderId(Integer id);
	
	@Transactional
	void deleteByOrderId(Integer id);
	
////	@Query(value="select new Produ(min(dateOrderAdded) as startDate, max(dateOrderAdded) as endDate) "
//////			+ "from PrestashopOrders")
//	@Query(value="select product_id,sum(quantity_sold), order_id from product_expiry_detail where order_id = 866 group by product_id")
//	List<ProductExpiryDetail> findProductIdAndQuantityPreparedAndOrderIdByOrderId(@Param("orderId")Integer id);

}
