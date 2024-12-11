package org.caansoft.sdfood.prestashop.service;

import java.util.List;

import org.caansoft.sdfood.prestashop.dto.CustomerDTO;

public interface CustomerService {

	public CustomerDTO getCustomer(Integer id);
	
	public List<CustomerDTO> getAllCustomers();
}
