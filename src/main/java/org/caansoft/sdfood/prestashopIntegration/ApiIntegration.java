package org.caansoft.sdfood.prestashopIntegration;
import org.caansoft.sdfood.prestashop.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

@Component
public class ApiIntegration {


    private static final Logger logger = LoggerFactory.getLogger(ApiIntegration.class);

    @Value("${prestashop.host}")
    private String prestashopHost;

    @Value("${prestashop.api.token}")
    private String prestashopApiToken;

    

    private String apiUrl;
    
    private static JAXBContext context = null;
    
    static {
        try {
            context = JAXBContext.newInstance(Prestashop.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public Prestashop getPrestaOrderDetails(String url, String resourceType) {

        apiUrl = prestashopHost + url;
        
        Prestashop prestashop;

        HttpMethod type = HttpMethod.GET;
        if (resourceType == "get") {
            type = HttpMethod.GET;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBasicAuth(prestashopApiToken, "");
            HttpEntity request = new HttpEntity(httpHeaders);
            ResponseEntity<Prestashop> response = restTemplate.exchange(apiUrl, type, request, Prestashop.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                prestashop = response.getBody();
                return prestashop;
            } else {
                logger.info("Api integration error code " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            logger.error("exception in getPrestaOrderDetails()" + e.getMessage());
        }
        return null;
    }

    public HttpStatus updaatePrestaOrder(String url, String resourceType, Prestashop obj) {

        apiUrl = prestashopHost + url;
        HttpMethod type = HttpMethod.PUT;
        
        Prestashop prestashop;
        
        if (resourceType == "put") {
            type = HttpMethod.PUT;
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBasicAuth(prestashopApiToken, "");
            HttpEntity<Prestashop> request = new HttpEntity<Prestashop>(obj, httpHeaders);
            ResponseEntity<Prestashop> response = restTemplate.exchange(apiUrl, type, request, Prestashop.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                prestashop = response.getBody();
                logger.info("Prestashop update order" + prestashop.getPrestashopOrderDetails());
                return HttpStatus.OK;
            } else {
                logger.info("Update order error api integration error code " + response.getStatusCodeValue());
                return response.getStatusCode();
            }
        } catch (Exception e) {
            logger.error("exception in updaatePrestaOrder()" + e.getMessage());
        }
        return null;
    }


    public HttpStatus postOrderMessage(String url, MessageDTO messageDTO) {

        apiUrl = prestashopHost + url;
        Prestashop prestashop = new Prestashop();
        prestashop.setMessageDTO(messageDTO);
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBasicAuth(prestashopApiToken, "");
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(prestashop, sw);

            System.out.println(sw.toString());
            HttpEntity<Prestashop> request = new HttpEntity<Prestashop>(prestashop, httpHeaders);
            
//            Marshaller m = context.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//
//            StringWriter sw = new StringWriter();
//            m.marshal(request, sw);
//
//            System.out.println(sw.toString());
            
            ResponseEntity<MessageDTO> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, MessageDTO.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                logger.info("Prestashop create message" + response.getBody());
                return response.getStatusCode();
            } else {
                logger.info("Error adding message to order" + response.getStatusCodeValue());
            }
        } catch (Exception e) {
        	e.printStackTrace();
            logger.error("exception in updaatePrestaOrder()" + e.getMessage());
        }
        return null;
    }


    public Prestashop getStatusDetail(String url) {

        apiUrl = prestashopHost + url;    	
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBasicAuth(prestashopApiToken, "");
            HttpEntity request = new HttpEntity(httpHeaders);
            ResponseEntity<Prestashop> response = restTemplate.exchange(apiUrl, HttpMethod.GET, request, Prestashop.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                logger.info("Api integration error code " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            logger.error("exception in getStatusDetail()" + e.getMessage());
        }
        return null;
    }

}
