package org.caansoft.sdfood.prestashop.serviceImpl;

import org.caansoft.sdfood.prestashop.dto.PrestashopAddressDTO;
import org.caansoft.sdfood.prestashop.service.PrestashopAddressService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PrestashopAddressServiceImpl implements PrestashopAddressService{

	@Autowired
	ApiIntegration api;
	
	@Value("${prestashop.host}")
	private String prestashopHost;
	
	@Override
	public PrestashopAddressDTO getAddress(Integer id) {
		PrestashopAddressDTO addressDTO = new PrestashopAddressDTO();
		String url = "/api/addresses/" + id + "/output_format=XML";
		Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
		addressDTO = prestashop.getPrestashopAddressDTO();
		return addressDTO;
	}
}
