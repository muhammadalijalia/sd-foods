package org.caansoft.sdfood.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.LocationsDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.model.Warehouse;
import org.caansoft.sdfood.model.Location;
import org.caansoft.sdfood.repo.WarehouseFloorRepository;
import org.caansoft.sdfood.service.WarehouseFloorService;
import org.caansoft.sdfood.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WarehouseFloorServiceImpl implements WarehouseFloorService {

	@Autowired
	private WarehouseFloorRepository warehouseFloorRepository;
	
	@Override
	public <T extends BaseDto> T add(T dto) {
		LocationsDto floorDto = (LocationsDto) dto;
		try {
			Location warehouse = ModelDtoConvertor.WAREHOUSE.dtoToModel(floorDto, Warehouse.class);
			warehouse = warehouseFloorRepository.save(warehouse);
			floorDto.setId(warehouse.getId());
			return (T) floorDto;
		} catch (Exception e) {

		}
		return null;
	}
	
	@Override
	public <T extends BaseDto> T findOne(long id) {
		Optional<Location> warehouseFloor = warehouseFloorRepository.findById(id);
		if (warehouseFloor.isPresent() && warehouseFloor.get().getFlag() == Flag.ACTIVE) {
			try {
				return ModelDtoConvertor.WAREHOUSE.modelToDto(warehouseFloor.get(), LocationsDto.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public void delete(long id) {
		Location warehouseFloor = warehouseFloorRepository.findById(id).get();
		warehouseFloor.setFlag(Flag.DELETED);
		warehouseFloorRepository.save(warehouseFloor);

	}

	@Override
	public <T extends BaseDto> T update(T dto) {

		return add(dto);
	}
	
	@Override
	public <T extends BaseDto> Page<T> find(Pageable pageable) {
		Page<Location> all = warehouseFloorRepository.findAllByFlag(pageable, Flag.ACTIVE);
		if (all.getSize() > 0) {
			List<LocationsDto> list = new ArrayList<>();
			LocationsDto dto = null;
			for (Location warehouse : all) {
				dto = new LocationsDto();
				BeanUtils.copyProperties(warehouse, dto);
				list.add(dto);
			}
			return (Page<T>) new PageImpl<LocationsDto>(list, pageable, all.getTotalElements());
		}
		return null;
	}

}
