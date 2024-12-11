package org.caansoft.sdfood.repo;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseRepository extends CrudRepository<Warehouse,Long>{
	Page<Warehouse> findAllByFlag(Pageable pageable, Flag flag);
}
