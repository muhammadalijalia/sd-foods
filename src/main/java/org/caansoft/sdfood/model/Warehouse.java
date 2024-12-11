package org.caansoft.sdfood.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.caansoft.core.model.BaseEntity;

@Entity
@Table(name ="warehouses")
public class Warehouse extends BaseEntity  {

	private String name;
	private String address;

	@OneToMany(mappedBy = "warehouse", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Location> locations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> warehouseList) {
		this.locations = warehouseList;
	}


}
