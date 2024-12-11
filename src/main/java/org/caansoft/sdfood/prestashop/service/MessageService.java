package org.caansoft.sdfood.prestashop.service;

import java.util.List;

import org.caansoft.sdfood.prestashop.dto.MessageDTO;

public interface MessageService {

	public List<MessageDTO> getAllMessages();
	
	public List<MessageDTO> getMessageList(List<Integer> list);
	
	public List<MessageDTO> getMessage(Integer id);
}
