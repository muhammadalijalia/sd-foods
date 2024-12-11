package org.caansoft.sdfood.prestashopIntegration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

public class AssociationsDto {

    private List<PrestashopCategory> categories;
    private List<StockAvailableDto> stock_availables;
    @XmlElementWrapper(name = "categories")
    @XmlElement(name = "category")
    public List<PrestashopCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PrestashopCategory> categories) {
        this.categories = categories;
    }
    @XmlElementWrapper(name = "stock_availables")
    @XmlElement(name = "stock_available")
    public List<StockAvailableDto> getStock_availables() {
        return stock_availables;
    }

    public void setStock_availables(List<StockAvailableDto> stock_availables) {
        this.stock_availables = stock_availables;
    }
    //    public List<StockAvailableDto> getStockAvailables() {
//        return stock_availables;
//    }
//
//    public void setStockAvailables(List<StockAvailableDto> stockAvailables) {
//        this.stock_availables = stockAvailables;
//    }


}
