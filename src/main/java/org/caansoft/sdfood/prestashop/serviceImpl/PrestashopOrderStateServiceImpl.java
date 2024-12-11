package org.caansoft.sdfood.prestashop.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.caansoft.sdfood.prestashop.dto.PrestashopOrderStateDTO;
import org.caansoft.sdfood.prestashop.service.PrestashopOrderStateService;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PrestashopOrderStateServiceImpl implements PrestashopOrderStateService{
	
	private static final Logger log = LoggerFactory.getLogger(PrestashopOrderStateServiceImpl.class);
	
	@Value("${prestashop.host}")
    private String prestashopHost;
	
	@Value("${prestashop.api.token}")
    private String prestashopApiToken;
	
	private Prestashop prestashop;

	@Override
	public Map<Integer, String> getOrderStates() {
		
		try {
			final String url = 
					prestashopHost + "/api/order_states/?output_format=XML&display=[id,name]";
			
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
			System.out.println(restTemplate.getMessageConverters().toString());
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setBasicAuth(prestashopApiToken, "");
			HttpEntity request = new HttpEntity(httpHeaders);
			ResponseEntity<Prestashop> response = restTemplate.exchange(url, HttpMethod.GET, request, Prestashop.class);
			System.out.println();
			
			if(response.getStatusCode() != null) {
				log.info("Order States body " + response.getBody());
				prestashop = response.getBody();
				Map<Integer, String> map = new HashMap<Integer, String>();
				for(PrestashopOrderStateDTO pos : prestashop.getPrestashopOrderStates()) {
					map.put(pos.getId(), pos.getName().get(0).getLanguage().get(0).getName());
				}
				return map;
			} else {
				log.info("order state api response code" + response.getStatusCode());
			}
		} catch(Exception e) {
			log.error("could not fetch the order states from prestashop");
			throw new RuntimeException("Exception occured while fetching the order states from prestashop " + e);
		}
		return null;
	}
	
	
}
