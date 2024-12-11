package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.PrestashopCategory;
import org.caansoft.sdfood.model.PrestashopProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestashopCategoryRepo extends JpaRepository<PrestashopCategory, Long> {
}
