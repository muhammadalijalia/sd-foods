package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.sdfood.prestashop.model.PrestashopOrderProducts;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product extends BaseEntity implements Serializable {

    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "sub_category_id")
    private Category subCategory;
    private String ref;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Integer quantity;
    private Integer numberOfunits;
    private Float price;
    private String unit;
    private String tva;
    private Boolean frozen;
    private Boolean isExpiry;
    private Boolean isFresh;
    @Column(name = "image_url")
    private String imageUrl;

    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public void setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
	}

	public Boolean getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(Boolean isExpiry) {
		this.isExpiry = isExpiry;
	}


	private Integer prestashopProductId;
//	    @ManyToOne
//	    @JoinColumn(name = "location_id")
//	    private Location location;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductName> productName = new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductStock> productStock = new ArrayList<>();

//	    @JsonManagedReference
//	    @ManyToMany(mappedBy = "product", cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
//	    private List<Order> order;
//	    
//	    @ManyToOne
//	    private Partner partners;

    public Product() {

    }

    public List<ProductName> getProductName() {
		return productName;
	}

	public void setProductName(List<ProductName> productName) {
		this.productName = productName;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Integer getPrestashopProductId() {
        return prestashopProductId;
    }

    public void setPrestashopProductId(Integer prestashopProductId) {
        this.prestashopProductId = prestashopProductId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    //		public Location getLocation() {
//			return location;
//		}
//		
//		public void setLocation(Location location) {
//			this.location = location;
//		}
//		
//		public List<OrderProduct> getOrderProducts() {
//			return orderProducts;
//		}
    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(Category subCategory) {
        this.subCategory = subCategory;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getHtPrice() {
        return price;
    }

    public void setHtPrice(Float htPrice) {
        this.price = htPrice;
    }

    public Integer getNumberOfunits() {
        return numberOfunits;
    }

    public void setNumberOfunits(Integer numberOfunits) {
        this.numberOfunits = numberOfunits;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }


//	    public Partner getPartners() {
//	        return partners;
//	    }
//
//	    public void setPartners(Partner partners) {
//	        this.partners = partners;
//	    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o)
            return true;
        // null check
        if (o == null)
            return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Product p = (Product) o;
        // field comparison
        return id.equals(p.getId());
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }
}