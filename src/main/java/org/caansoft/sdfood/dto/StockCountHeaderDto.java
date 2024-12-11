package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;
import org.caansoft.sdfood.enums.StockCountStatus;

import java.util.List;

/**
 * Created by shoaib on 07/03/2023.
 */

public class StockCountHeaderDto extends BaseDto {

    private StockCountStatus status;

    private List<StockStatusHistoryDto> stockStatusHistoryDtos;
    private List<StockCountLineItemDto> stockCountLineItemDtos;
    public StockCountStatus getStatus() {
        return status;
    }

    public void setStatus(StockCountStatus status) {
        this.status = status;
    }

    public List<StockStatusHistoryDto> getStockStatusHistoryDtos() {
        return stockStatusHistoryDtos;
    }

    public void setStockStatusHistoryDtos(List<StockStatusHistoryDto> stockStatusHistoryDtos) {
        this.stockStatusHistoryDtos = stockStatusHistoryDtos;
    }

    public List<StockCountLineItemDto> getStockCountLineItemDtos() {
        return stockCountLineItemDtos;
    }

    public void setStockCountLineItemDtos(List<StockCountLineItemDto> stockCountLineItemDtos) {
        this.stockCountLineItemDtos = stockCountLineItemDtos;
    }
}
