package org.caansoft.sdfood.prestashop.serviceImpl;

import java.util.List;

import org.caansoft.sdfood.prestashop.dto.MessageDTO;
import org.caansoft.sdfood.prestashop.service.MessageService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	ApiIntegration api;
	
	@Value("${prestashop.host}")
	private String prestashopHost;
	
	@Override
	public List<MessageDTO> getAllMessages() {
		
		String url = "/api/messages?display=full&output_format=XML";
		Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
		List<MessageDTO> messageDTOList = prestashop.getMessageDTOList();
		if(!messageDTOList.isEmpty()) {
			return messageDTOList;
		}
		return null;
	}

	@Override
	public List<MessageDTO> getMessageList(List<Integer> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MessageDTO> getMessage(Integer id) {

		String url = "/api/messages?display=full&filter[id_order]=" + id+ "&output_format=XML";
		Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
		List<MessageDTO> messageDTOList = prestashop.getMessageDTOList();
		if(!messageDTOList.isEmpty()) {
			return messageDTOList;
		}
		return null;
	}

}
