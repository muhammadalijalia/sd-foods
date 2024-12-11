package org.caansoft.sdfood.repo;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.model.Category;
import org.caansoft.sdfood.model.StockCountHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockCountRepository extends PagingAndSortingRepository<StockCountHeader, Long>, JpaSpecificationExecutor<StockCountHeader> {
    Page<Category> findAllByFlag(Pageable pageable, Flag flag);
}
