package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.ProductName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductNameRepository extends JpaRepository<ProductName, Long>{

	public ProductName findByNameAndProduct(String name, Product id);
}
