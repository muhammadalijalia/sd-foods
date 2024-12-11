package org.caansoft.sdfood.repo;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface WarehouseFloorRepository extends CrudRepository<Location,Long>{
	Page<Location> findAllByFlag(Pageable pageable, Flag flag);
}
