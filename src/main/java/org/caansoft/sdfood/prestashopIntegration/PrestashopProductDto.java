package org.caansoft.sdfood.prestashopIntegration;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

public class PrestashopProductDto {

    private Integer id;
    private Integer manufacturerId;
    private Integer supplierId;
    private Integer defaultCategoryId;
    private List<PrestashopProductName> name;
    private Float price;
    private PrestashopLinkRewrite linkRewrite;

    private Integer state;
    private Integer active;
    private String reference;
    private Integer availableForOrder;
    private AssociationsDto associations;
    private String idTaxRulesGroup;
    private String idDefaultImage;

    @XmlElement(name = "id_default_image")
    public String getIdDefaultImage() {
		return idDefaultImage;
	}

	public void setIdDefaultImage(String idDefaultImage) {
		this.idDefaultImage = idDefaultImage;
	}

	@XmlElement(name = "id_tax_rules_group")
    public String getIdTaxRulesGroup() {
		return idTaxRulesGroup;
	}

	public void setIdTaxRulesGroup(String idTaxRulesGroup) {
		this.idTaxRulesGroup = idTaxRulesGroup;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    public List<PrestashopProductName> getName() {
        return name;
    }
    public void setName(List<PrestashopProductName> name) {
        this.name = name;
    }

    @XmlElement(name = "id_category_default")
    public Integer getDefaultCategoryId() {
        return defaultCategoryId;
    }

    public void setDefaultCategoryId(Integer defaultCategoryId) {
        this.defaultCategoryId = defaultCategoryId;
    }

    @XmlElement(name = "id_supplier")
    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    @XmlElement(name = "id_manufacturer")
    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }
    @XmlElement(name = "link_rewrite")
    public PrestashopLinkRewrite getLinkRewrite() {
        return linkRewrite;
    }

    public void setLinkRewrite(PrestashopLinkRewrite linkRewrite) {
        this.linkRewrite = linkRewrite;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @XmlElement(name = "available_for_order")
    public Integer getAvailableForOrder() {
        return availableForOrder;
    }

    public void setAvailableForOrder(Integer availableForOrder) {
        this.availableForOrder = availableForOrder;
    }

    public AssociationsDto getAssociations() {
        return associations;
    }

    public void setAssociations(AssociationsDto associations) {
        this.associations = associations;
    }
}

