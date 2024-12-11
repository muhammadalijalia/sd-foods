package org.caansoft.sdfood.serviceimpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.caansoft.core.common.NullAwareBeanUtilsBean;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.WarehouseDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.model.Warehouse;
import org.caansoft.sdfood.repo.WarehouseRepository;
import org.caansoft.sdfood.service.WarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Override
	public <T extends BaseDto> T add(T dto) {
		WarehouseDto warehouseDto = (WarehouseDto) dto;
		try {
			Warehouse warehouse = ModelDtoConvertor.WAREHOUSE.dtoToModel(warehouseDto, Warehouse.class);
			warehouse = warehouseRepository.save(warehouse);
			warehouseDto.setId(warehouse.getId());
			return (T) warehouseDto;
		} catch (Exception e) {

		}
		return null;
	}
	
	@Override
	public <T extends BaseDto> T findOne(long id) {
		Optional<Warehouse> warehouse = warehouseRepository.findById(id);
		if (warehouse.isPresent() && warehouse.get().getFlag() == Flag.ACTIVE) {
			try {
				return ModelDtoConvertor.WAREHOUSE.modelToDto(warehouse.get(), WarehouseDto.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public void delete(long id) {
		Warehouse warehouse = warehouseRepository.findById(id).get();
		warehouse.setFlag(Flag.DELETED);
		warehouseRepository.save(warehouse);
	}

	@Override
	public <T extends BaseDto> T update(T dto) {
//		WarehouseDto warehouseDto = (WarehouseDto) dto;
		 try {
//	            ModelDtoConvertor.setUserService(userService);
//	            ModelDtoConvertor.setOrderRepo(orderRepository);
//	            GenericConverter.setProductServiceRepository(productServiceRepository);
	            Warehouse warehouse = ModelDtoConvertor.WAREHOUSE.dtoToModel(dto, Warehouse.class);
//	            if(order.getStatus().equals(OrderStatus.WAITING_FOR_DOCS)){
//	                order.setInvoiceNumber(generateInvoiceNumber());
//	                order.setInvoiceDate(Timestamp.valueOf(LocalDateTime.now()));
//	            }
	            Optional<Warehouse> dbOrderOptional = warehouseRepository.findById(warehouse.getId());
	            if (dbOrderOptional.isPresent()) {
	                Warehouse dbWarehouse = dbOrderOptional.get();
	                dbWarehouse.getLocations().clear();
	                List<org.caansoft.sdfood.model.Location> location = warehouse.getLocations();
	                warehouse.setLocations(null);
	                NullAwareBeanUtilsBean.get().copyProperties(dbWarehouse, warehouse);
	                dbWarehouse.getLocations().addAll(location);
	                warehouse = warehouseRepository.save(dbWarehouse);
	            }
	            dto = ModelDtoConvertor.WAREHOUSE.modelToDto(warehouse, WarehouseDto.class);
	          
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
		return add(dto);
	}
	
	@Override
	public <T extends BaseDto> Page<T> find(Pageable pageable) {
		Page<Warehouse> all = warehouseRepository.findAllByFlag(pageable, Flag.ACTIVE);
		if (all.getSize() > 0) {
			List<WarehouseDto> list = new ArrayList<>();
			WarehouseDto dto = null;
			for (Warehouse warehouse : all) {
				try{
					dto = ModelDtoConvertor.WAREHOUSE.modelToDto(warehouse, WarehouseDto.class);
//				BeanUtils.copyProperties(category, dto);
					list.add(dto);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			return (Page<T>) new PageImpl<WarehouseDto>(list, pageable, all.getTotalElements());
		}
		return null;
	}

}
