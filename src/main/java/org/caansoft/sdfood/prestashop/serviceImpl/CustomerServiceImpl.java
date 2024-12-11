package org.caansoft.sdfood.prestashop.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.caansoft.sdfood.prestashop.dto.CustomerDTO;
import org.caansoft.sdfood.prestashop.service.CustomerService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private ApiIntegration api;
	
	Prestashop prestashop;

	@Override
	public CustomerDTO getCustomer(Integer id) {
		
		CustomerDTO customerDTO = new CustomerDTO();
		String url = "/api/customers/" + id + "?output_format=XML";
		prestashop = api.getPrestaOrderDetails(url, "get");
		if(prestashop != null && prestashop.getCustomer() != null) {
			customerDTO = prestashop.getCustomer();
			return customerDTO;
		}
		return null;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		List<CustomerDTO> customerDTO = new ArrayList<CustomerDTO>();
		String url = "/api/customers?display=full&output_format=XML";
		prestashop = api.getPrestaOrderDetails(url, "get");
		if(prestashop != null && prestashop.getCustomerList() != null) {
			customerDTO = prestashop.getCustomerList();
			return customerDTO;
		}
		return null;
	}

}
