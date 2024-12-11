package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.sdfood.enums.StockCountStatus;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by shoaib on 07/03/2023.
 */
@Entity
@Table(name = "stock_status_history")
public class StockStatusHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "stockcount_header_id")
    private StockCountHeader stockCountHeader;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StockCountStatus status;
    @Column(length = 1000)
    private String comments;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;

    public StockCountHeader getStockCountHeader() {
        return stockCountHeader;
    }

    public void setStockCountHeader(StockCountHeader stockCountHeader) {
        this.stockCountHeader = stockCountHeader;
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
