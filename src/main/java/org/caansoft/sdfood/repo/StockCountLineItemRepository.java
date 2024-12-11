package org.caansoft.sdfood.repo;

import java.util.List;

import org.caansoft.sdfood.model.StockCountHeader;
import org.caansoft.sdfood.model.StockCountLineItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockCountLineItemRepository extends PagingAndSortingRepository<StockCountLineItem, Long>, JpaSpecificationExecutor<StockCountLineItem> {
	
	List<StockCountLineItem> findBystockCountHeader(StockCountHeader header);
}
