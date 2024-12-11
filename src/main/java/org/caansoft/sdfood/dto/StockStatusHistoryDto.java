package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;
import org.caansoft.sdfood.enums.StockCountStatus;

import java.sql.Timestamp;

/**
 * Created by shoaib on 07/03/2023.
 */

public class StockStatusHistoryDto extends BaseDto {

    private StockCountHeaderDto stockCountHeaderDto;
    private StockCountStatus status;
    private String comments;
    private Timestamp startDate;
    private Timestamp endDate;

    public StockCountHeaderDto getStockCountHeaderDto() {
        return stockCountHeaderDto;
    }

    public void setStockCountHeaderDto(StockCountHeaderDto stockCountHeaderDto) {
        this.stockCountHeaderDto = stockCountHeaderDto;
    }

    public StockCountStatus getStatus() {
        return status;
    }

    public void setStatus(StockCountStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

}
