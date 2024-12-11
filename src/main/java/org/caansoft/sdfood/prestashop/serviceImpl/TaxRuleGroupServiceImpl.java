package org.caansoft.sdfood.prestashop.serviceImpl;

import java.util.List;

import org.caansoft.sdfood.prestashop.dto.TaxRuleGroupDto;
import org.caansoft.sdfood.prestashop.service.TaxRuleGroupService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TaxRuleGroupServiceImpl implements TaxRuleGroupService{
	
	private static final Logger log = LoggerFactory.getLogger(TaxRuleGroupServiceImpl.class);
	
	@Autowired
	ApiIntegration api;

	@Override
	public List<TaxRuleGroupDto> getTaxRuleGroups() {
		
		String url = "/api/tax_rule_groups?display=full&output_format=XML";
		try {
			Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
			if (prestashop == null) {
				log.info("No details for tax rule groups");
				throw new RuntimeException("could get tax rule groups");
			}
			List<TaxRuleGroupDto> dtoList = prestashop.getTaxRuleGroupList();
			if (dtoList != null && !dtoList.isEmpty()) {
				return dtoList;
			}
		} catch (Exception e) {
			log.error("Error occured while fetching the prestashop tax rules");
			e.printStackTrace();
		}
		return null;
	}

}
