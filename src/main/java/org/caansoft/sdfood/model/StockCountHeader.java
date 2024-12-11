package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.sdfood.enums.StockCountStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by shoaib on 07/03/2023.
 */
@Entity
@Table(name = "stockcount_header")
public class StockCountHeader extends BaseEntity {

    private StockCountStatus status;

    @OneToMany(mappedBy = "stockCountHeader", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StockStatusHistory> stockStatusHistories;


    @OneToMany(mappedBy = "stockCountHeader", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StockCountLineItem> stockCountLineItems;

    public StockCountStatus getStatus() {
        return status;
    }

    public void setStatus(StockCountStatus status) {
        this.status = status;
    }

    public List<StockStatusHistory> getStockStatusHistories() {
        return stockStatusHistories;
    }

    public void setStockStatusHistories(List<StockStatusHistory> stockStatusHistories) {
        this.stockStatusHistories = stockStatusHistories;
    }

    public List<StockCountLineItem> getStockCountLineItems() {
        return stockCountLineItems;
    }

    public void setStockCountLineItems(List<StockCountLineItem> stockCountLineItems) {
        this.stockCountLineItems = stockCountLineItems;
    }
}
