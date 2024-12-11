package org.caansoft.sdfood.prestashopIntegration;

import org.caansoft.sdfood.prestashop.dto.CustomerDTO;
import org.caansoft.sdfood.prestashop.dto.MessageDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopAddressDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderDetailsDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderStateDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrdersDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopStockMovementDto;
import org.caansoft.sdfood.prestashop.dto.TaxRuleGroupDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "prestashop")
public class Prestashop {
    //    private PrestashopAddressDto address;
//
//    @XmlElement(name = "address")
//    public PrestashopAddressDto getAddress() {
//        return address;
//    }
//    public void setAddress(PrestashopAddressDto address) {
//        this.address = address;
//    }
	private PrestashopProductDto product;
    private PrestashopCategory category;
//    private StockAvailableDto stock;
    private StockAvailableDto stock_available;
    private List<PrestashopLanguage> languages;                    
	private List<PrestashopOrdersDTO> prestashopOrdersList;
	private PrestashopOrdersDTO prestashopOrderDetails;
	private List<PrestashopOrderStateDTO> prestashopOrderStates;
	private PrestashopOrderStateDTO prestashopOrderState;
	
	@XmlElement(name="order_state")
	public PrestashopOrderStateDTO getPrestashopOrderState() {
		return prestashopOrderState;
	}
	public void setPrestashopOrderState(PrestashopOrderStateDTO prestashopOrderState) {
		this.prestashopOrderState = prestashopOrderState;
	}
	private PrestashopOrderDetailsDTO prestashopOrderDetailsDTO;
	private List<PrestashopOrderDetailsDTO> prestashopOrderDetailList;
	private List<CustomerDTO> customerList;
	private CustomerDTO customer;
	private PrestashopAddressDTO prestashopAddressDTO;
	private List<PrestashopAddressDTO> prestashopAddressDTOList;
	private MessageDTO messageDTO;
	private List<MessageDTO> messageDTOList;
	private List<TaxRuleGroupDto> taxRuleGroupList;
	private List<PrestashopStockMovementDto> prestashopStockMovementList;
	private PrestashopStockMovementDto prestashopStockMovementDto;
	private List<StockAvailableDto> stockAvailableList;
	private List<StockAvailableDto> stockAvailableListPost;

	@XmlElement(name="stock_availables")
	public List<StockAvailableDto> getStockAvailableListPost() {
		return stockAvailableListPost;
	}
	public void setStockAvailableListPost(List<StockAvailableDto> stockAvailableListPost) {
		this.stockAvailableListPost = stockAvailableListPost;
	}
	@XmlElementWrapper(name="stock_availables")
	@XmlElement(name="stock_available")
	public List<StockAvailableDto> getStockAvailableList() {
		return stockAvailableList;
	}
	public void setStockAvailableList(List<StockAvailableDto> stockAvailableList) {
		this.stockAvailableList = stockAvailableList;
	}
	@XmlElementWrapper(name="stock_mvts")
	@XmlElement(name="stock_mvt")
	public List<PrestashopStockMovementDto> getPrestashopStockMovementList() {
		return prestashopStockMovementList;
	}
	public void setPrestashopStockMovementList(List<PrestashopStockMovementDto> prestashopStockMovementList) {
		this.prestashopStockMovementList = prestashopStockMovementList;
	}
	private List<PrestashopProductDto> productsList;
	
	@XmlElement(name="stock_mvt")
	public PrestashopStockMovementDto getPrestashopStockMovementDto() {
		return prestashopStockMovementDto;
	}
	public void setPrestashopStockMovementDto(PrestashopStockMovementDto prestashopStockMovementDto) {
		this.prestashopStockMovementDto = prestashopStockMovementDto;
	}
	@XmlElementWrapper(name="tax_rule_groups")
	@XmlElement(name="tax_rule_group")
	public List<TaxRuleGroupDto> getTaxRuleGroupList() {
		return taxRuleGroupList;
	}
	public void setTaxRuleGroupList(List<TaxRuleGroupDto> taxRuleGroupList) {
		this.taxRuleGroupList = taxRuleGroupList;
	}
	@XmlElement(name="message")
	public MessageDTO getMessageDTO() {
		return messageDTO;
	}
		
	public void setMessageDTO(MessageDTO messageDTO) {
		this.messageDTO = messageDTO;
	}
	
	@XmlElementWrapper(name="products")
	@XmlElement(name="product")
	public List<PrestashopProductDto> getProductsList() {
		return productsList;
	}
	public void setProductsList(List<PrestashopProductDto> productsList) {
		this.productsList = productsList;
	}
	@XmlElementWrapper(name="messages")
	@XmlElement(name="message")
	public List<MessageDTO> getMessageDTOList() {
		return messageDTOList;
	}
	public void setMessageDTOList(List<MessageDTO> messageDTOList) {
		this.messageDTOList = messageDTOList;
	}
	@XmlElement(name="address")
	public PrestashopAddressDTO getPrestashopAddressDTO() {
		return prestashopAddressDTO;
	}
	public void setPrestashopAddressDTO(PrestashopAddressDTO prestashopAddressDTO) {
		this.prestashopAddressDTO = prestashopAddressDTO;
	}

	@XmlElementWrapper(name="addresses")
	@XmlElement(name="address")
	public List<PrestashopAddressDTO> getPrestashopAddressDTOList() {
		return prestashopAddressDTOList;
	}
	public void setPrestashopAddressDTOList(List<PrestashopAddressDTO> prestashopAddressDTOList) {
		this.prestashopAddressDTOList = prestashopAddressDTOList;
	}

	@XmlElementWrapper(name="customers")
	@XmlElement(name="customer")
	public List<CustomerDTO> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<CustomerDTO> customerList) {
		this.customerList = customerList;
	}

	@XmlElement(name="customer")
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}
	@XmlElement(name="order_detail")
	public PrestashopOrderDetailsDTO getPrestashopOrderDetailsDTO() {
		return prestashopOrderDetailsDTO;
	}
	public void setPrestashopOrderDetailsDTO(PrestashopOrderDetailsDTO prestashopOrderDetailsDTO) {
		this.prestashopOrderDetailsDTO = prestashopOrderDetailsDTO;
	}

	@XmlElementWrapper(name="order_details")
	@XmlElement(name="order_detail")
	public List<PrestashopOrderDetailsDTO> getPrestashopOrderDetailList() {
		return prestashopOrderDetailList;
	}
	public void setPrestashopOrderDetailList(List<PrestashopOrderDetailsDTO> prestashopOrderDetailList) {
		this.prestashopOrderDetailList = prestashopOrderDetailList;
	}
	@XmlElementWrapper(name="order_states")
	@XmlElement(name="order_state")
    public List<PrestashopOrderStateDTO> getPrestashopOrderStates() {
        return prestashopOrderStates;
    }

    public void setPrestashopOrderStates(List<PrestashopOrderStateDTO> prestashopOrderStates) {
        this.prestashopOrderStates = prestashopOrderStates;
    }

    @XmlElement(name = "order")
    public PrestashopOrdersDTO getPrestashopOrderDetails() {
        return prestashopOrderDetails;
    }

    public void setPrestashopOrderDetails(PrestashopOrdersDTO prestashopOrderDetails) {
        this.prestashopOrderDetails = prestashopOrderDetails;
    }

    @XmlElement(name = "product")
    public PrestashopProductDto getProduct() {
        return product;
    }

    public void setProduct(PrestashopProductDto product) {
        this.product = product;
    }

    public PrestashopCategory getCategory() {
        return category;
    }

    public void setCategory(PrestashopCategory category) {
        this.category = category;
    }

    @XmlElement(name = "stock_available")
    public StockAvailableDto getStock_available() {
        return stock_available;
    }

    public void setStock_available(StockAvailableDto stock_available) {
        this.stock_available = stock_available;
    }

//    @XmlElement(name = "stock]")
//
//    public StockAvailableDto getStock() {
//        return stock;
//    }
//
//    public void setStock(StockAvailableDto stock) {
//        this.stock = stock;
//    }

    public List<PrestashopLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<PrestashopLanguage> languages) {
        this.languages = languages;
    }

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    public List<PrestashopOrdersDTO> getPrestashopOrdersList() {
        return prestashopOrdersList;
    }

    public void setPrestashopOrdersList(List<PrestashopOrdersDTO> prestashopOrdersList) {
        this.prestashopOrdersList = prestashopOrdersList;
    }


}

