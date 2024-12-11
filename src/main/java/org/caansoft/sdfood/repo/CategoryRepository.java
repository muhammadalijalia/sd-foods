package org.caansoft.sdfood.repo;

import org.caansoft.core.enums.Flag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.caansoft.sdfood.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    Page<Category> findAllByFlag(Pageable pageable, Flag flag);
}
