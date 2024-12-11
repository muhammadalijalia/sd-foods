package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StatsDto extends BaseDto {

    private Integer newOrders;
    private Integer validatedOrders;
    private Integer inTransitOrders;
    private Integer receivedOrders;
    private Integer stockedOrders;
    private Integer cancelledOrders;
    private Float damagedQuantity;
    private Double totalSaleAmount;
    private Long totalSalesQuantity;
	public Long getTotalSalesQuantity() {
		return totalSalesQuantity;
	}

	public void setTotalOrders(Long totalSalesQuantity) {
		this.totalSalesQuantity = totalSalesQuantity;
	}

	private List<DamagedExpiredLocationWise> damagedLocationWiseCounts;
    private List<DamagedExpiredLocationWise> expiredLocationWiseCounts;
    private int expiryDaysThreshold;
    
    public Double getTotalSaleAmount() {
 		return totalSaleAmount;
 	}

 	public void setTotalSaleAmount(Double totalSaleAmount) {
 		this.totalSaleAmount = totalSaleAmount;
 	}

    public Float getDamagedQuantity() {
        return damagedQuantity;
    }

    public void setDamagedQuantity(Float damagedQuantity) {
        this.damagedQuantity = damagedQuantity;
    }

    public Integer getInTransitOrders() {
        return inTransitOrders;
    }

    public void setInTransitOrders(Integer inTransitOrders) {
        this.inTransitOrders = inTransitOrders;
    }

    public Integer getReceivedOrders() {
        return receivedOrders;
    }

    public void setReceivedOrders(Integer receivedOrders) {
        this.receivedOrders = receivedOrders;
    }

    public Integer getStockedOrders() {
        return stockedOrders;
    }

    public void setStockedOrders(Integer stockedOrders) {
        this.stockedOrders = stockedOrders;
    }

    public Integer getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Integer cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public Integer getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(Integer newOrders) {
        this.newOrders = newOrders;
    }

    public Integer getValidatedOrders() {
        return validatedOrders;
    }

    public void setValidatedOrders(Integer validatedOrders) {
        this.validatedOrders = validatedOrders;
    }

    public List<DamagedExpiredLocationWise> getDamagedLocationWiseCounts() {
        return damagedLocationWiseCounts;
    }

    public void setDamagedLocationWiseCounts(List<DamagedExpiredLocationWise> damagedLocationWiseCounts) {
        this.damagedLocationWiseCounts = damagedLocationWiseCounts;
    }

    public List<DamagedExpiredLocationWise> getExpiredLocationWiseCounts() {
        return expiredLocationWiseCounts;
    }

    public void setExpiredLocationWiseCounts(List<DamagedExpiredLocationWise> expiredLocationWiseCounts) {
        this.expiredLocationWiseCounts = expiredLocationWiseCounts;
    }

    public int getExpiryDaysThreshold() {
        return expiryDaysThreshold;
    }

    public void setExpiryDaysThreshold(int expiryDaysThreshold) {
        this.expiryDaysThreshold = expiryDaysThreshold;
    }

    public static class DamagedExpiredLocationWise {

        private float count;
        private ProductDto productDto;
        private LocationsDto locationsDto;
        private int daysToExpire;

        public DamagedExpiredLocationWise() {
        }

        public DamagedExpiredLocationWise(float count, ProductDto productDto, LocationsDto locationsDto) {
            this.count = count;
            this.productDto = productDto;
            this.locationsDto = locationsDto;
        }


        public DamagedExpiredLocationWise(float count, ProductDto productDto, LocationsDto locationsDto, int daysToExpire) {
            this.count = count;
            this.productDto = productDto;
            this.locationsDto = locationsDto;
            this.daysToExpire = daysToExpire;
        }

        public float getCount() {
            return count;
        }

        public void setCount(float count) {
            this.count = count;
        }

        public ProductDto getProductDto() {
            return productDto;
        }

        public void setProductDto(ProductDto productDto) {
            this.productDto = productDto;
        }

        public LocationsDto getLocationsDto() {
            return locationsDto;
        }

        public void setLocationsDto(LocationsDto locationsDto) {
            this.locationsDto = locationsDto;
        }

        public int getDaysToExpire() {
            return daysToExpire;
        }

        public void setDaysToExpire(int daysToExpire) {
            this.daysToExpire = daysToExpire;
        }
    }

}
