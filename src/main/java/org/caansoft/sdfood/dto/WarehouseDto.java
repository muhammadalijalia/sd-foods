package org.caansoft.sdfood.dto;


import java.util.List;

import org.caansoft.core.dto.BaseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WarehouseDto extends BaseDto {

	private String name;

	private String address;

	//	private List<String> locations;
	@JsonProperty(value = "locations")
	private List<LocationsDto> locationsList;

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

	public List<LocationsDto> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<LocationsDto> locationsList) {
		this.locationsList = locationsList;
	}


}
