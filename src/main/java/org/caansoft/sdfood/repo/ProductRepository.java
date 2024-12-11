package org.caansoft.sdfood.repo;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAllByFlag(Pageable pageable, Flag flag);

    //    Specification<OrderProductLocation> findAllByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date toDate, Date fromDate);
//    Page<Product> findByNameAndExpiry(String name);(Pageable pageable, Flag flag);
      List<Product> findByName(String name);
      
      Optional<Product> findById(Long id);
      
      List<Product> findByIdIn(List<Long> ids);
      
      List<Product> findByPrestashopProductIdIn(List<Integer> ids);
      
      List<Product> findByImageUrlIsNull();
      
      default Map<Integer, Product> findByPrestashopProductIdMap(List<Integer> ids) {
    	  return findByPrestashopProductIdIn(ids).stream().collect(Collectors.toMap
    			  (Product :: getPrestashopProductId, v -> v));
      }
      
      List<Product> findAllByPrestashopProductIdIn(List<Integer> ids);
}
