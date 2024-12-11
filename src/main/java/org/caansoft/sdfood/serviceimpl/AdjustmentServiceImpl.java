package org.caansoft.sdfood.serviceimpl;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.ProductStockBalanceDTO;
import org.caansoft.sdfood.dto.ProductStockQuantityDTO;
import org.caansoft.sdfood.enums.ProductStockTransactionType;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.ProductStock;
import org.caansoft.sdfood.repo.ProductRepository;
import org.caansoft.sdfood.repo.ProductStockRepository;
import org.caansoft.sdfood.request.AdjustmentRequest;
import org.caansoft.sdfood.service.AdjustmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdjustmentServiceImpl implements AdjustmentService {

    @Autowired
    ProductStockRepository productStockRepo;
    
    @Autowired
    ProductRepository productRepo;
    
    private static final Logger logger = LoggerFactory.getLogger(AdjustmentServiceImpl.class);

	@Override	
	public Object getAdjustmentDetails(AdjustmentRequest request) {

		try {

			if (request != null) {
				ProductStock ps = new ProductStock();
				Integer adjustment = (request.getInitialValues().getBalance() == null ? 0
						: Math.abs(request.getInitialValues().getBalance())) - request.getInitialValues().getQuantity();

				if (adjustment == 0) {
					if (request.getInitialValues().getBalance() < 0)
						ps.setQuantityIn(request.getInitialValues().getQuantity());
					if (request.getInitialValues().getBalance() > 0)
						ps.setQuantityOut(request.getInitialValues().getQuantity());
				}

				if (adjustment > 0) {
					ps.setQuantityOut(adjustment);
				} else if (adjustment < 0) {
					ps.setQuantityIn(Math.abs(adjustment));
				}

				ps.setInputStockCount((request.getInitialValues().getQuantity()));
				ps.setExprityDate(request.getInitialValues().getExpiry() == null ? null
						: new Timestamp(request.getInitialValues().getExpiry()));
				ps.setExternalSystemId(request.getInitialValues().getExternalSystemId() == null ? null
						: request.getInitialValues().getExternalSystemId());
				ps.setLotNumber(request.getInitialValues().getLotNumber() == null ? null
						: request.getInitialValues().getLotNumber());
				Optional<Product> product = productRepo.findById(request.getInitialValues().getId().longValue());
				if (product.isPresent()) {
					ps.setProduct(product.get());
					ps.setPrestashopProductId(product.get().getPrestashopProductId() != null ? 
							product.get().getPrestashopProductId().longValue() : null);
				}

				if (request.getInitialValues().getRecordedDate() != null
						&& request.getInitialValues().getRecordedTime() != null) {
					ps.setRecordedDate(new Timestamp(request.getInitialValues().getRecordedDate()
							+ request.getInitialValues().getRecordedTime()));
					ps.setCreatedOn(new Timestamp(request.getInitialValues().getRecordedDate()
							+ request.getInitialValues().getRecordedTime()));
				}
				ps.setRecordedDate(new Timestamp(request.getInitialValues().getSelectedDate()));
				ps.setCreatedOn(new Timestamp(request.getInitialValues().getSelectedDate()));
				ps.setTransactionType(ProductStockTransactionType.ADJUSTMENT);

//				ProductStock maxValue = productStockRepo.findMaxId();
//				ps.setId(maxValue.getId() + 1);
				productStockRepo.save(ps);
				return ps;
			}
		} catch (Exception e) {
			logger.error("exception occured while saving the adjustment for product stock");
			throw new RuntimeException("Could not complete the adjustment for this product due to: " + e);			
		}

//    	if (request != null) {
//    		ProductStock ps = new ProductStock();
//    		if (request.getInitialValues().getQuantity() > 0) {
//    			ps.setQuantityIn(request.getInitialValues().getQuantity());
//    		} else if (request.getInitialValues().getQuantity() < 0) {
//    			ps.setQuantityOut(Math.abs(request.getInitialValues().getQuantity()));
//    		}
//    		ps.setBalance(request.getInitialValues().getBalance() == null ? null : request.getInitialValues().getBalance());
//    		ps.setExprityDate(request.getInitialValues().getExpiry() == null ? null : new Timestamp(request.getInitialValues().getExpiry()));
//    		ps.setExternalSystemId(request.getInitialValues().getExternalSystemId() == null ? null : request.getInitialValues().getExternalSystemId());
//    		ps.setLotNumber(request.getInitialValues().getLotNumber() == null ? null : request.getInitialValues().getLotNumber());
//    		ps.setProductId(request.getInitialValues().getId());
//	        ps.setRecordedDate(new Timestamp(request.getInitialValues().getSelectedDate()));
//	        ps.setTransactionType("Adjustment");
//	        productStockRepo.save(ps);
//	        return ps;
//    	}

		return null;
	}

    @Override
    public ProductStockBalanceDTO productbalanceDetails(Long id, Long date) {
    	
    	Optional<Product> product = productRepo.findById(id);
    	if (product.isPresent()) {
    		ProductStock ps = productStockRepo.findQuantitySum(product.get(), new Timestamp(date));
            if (ps != null) {
                ProductStockBalanceDTO balanceDTO = new ProductStockBalanceDTO();
                balanceDTO.setBalance((ps.getSumQuantityIn() != null ? ps.getSumQuantityIn() : 0) - (ps.getSumQuantityOut() != null ? ps.getSumQuantityOut() : 0));
                balanceDTO.setId(ps.getProduct().getId());
                return balanceDTO;
            }          
    	}
		return null;        
    }

	@Override
	public Page<ProductStockQuantityDTO> getProductStockCount(String productName, Long startDate, Long endDate, Flag flag, Pageable pageable, Boolean isPageable) {							
		
		Timestamp sDate = startDate != null ? new Timestamp(startDate) : null;
		Timestamp eDate = endDate != null ? new Timestamp(endDate) : null;
		
		Page<ProductStock> productStock = productStockRepo.findProductStock(productName, sDate, eDate, flag, !isPageable ? Pageable.unpaged() : pageable);
		if (productStock != null && !productStock.isEmpty()) {
			List<ProductStockQuantityDTO> stockQuantityDTOList = new ArrayList<>();
			for (ProductStock ps : productStock) {
				ProductStockQuantityDTO dto = new ProductStockQuantityDTO();
				dto.setId(ps.getProduct() == null ? null : ps.getProduct().getId());
				dto.setName(ps.getProductName() == null ? null : ps.getProductName());				
				dto.setTotalQuantity(ps.getSumQuantityIn() - ps.getSumQuantityOut());
				stockQuantityDTOList.add(dto);
			}
			Page<ProductStockQuantityDTO> page = new PageImpl<ProductStockQuantityDTO>(stockQuantityDTOList,
					pageable, productStock.getTotalElements());
			return page;
		}
		return null;
	}
}
