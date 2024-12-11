package org.caansoft.sdfood.prestashopIntegration;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.dto.StockUpdateDTO;
import org.caansoft.sdfood.model.Category;
import org.caansoft.sdfood.model.OrderProduct;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.StockCountLineItem;
import org.caansoft.sdfood.repo.ProductRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PrestashopService {

    @Value("${prestashop.host:https://sdfoods.caansoft.com}")
    private String prestashopHost;

    @Value("${prestashop.api.token:1NQMLMAY7I98QRKXHVE5ABF7QK6V5YXH}")
    private String prestashopApiToken;
    
    @Autowired
    private ApiIntegration api;

    private static JAXBContext context = null;
    
    @Autowired
    private ProductRepository productRepo;

    static {
        try {
            context = JAXBContext.newInstance(Prestashop.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public PrestashopCategory getCategory(Integer categoryId) {
        String url = prestashopHost + "/api/categories/" + categoryId;

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(prestashopApiToken,"");
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Prestashop> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody().getCategory();
            }
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }
    public PrestashopCategory addCategory(Category category) {
        String url = prestashopHost + "/api/categories/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(prestashopApiToken,"");
        ResponseEntity<Prestashop> response = null;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpEntity request = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url + "?schema=blank",
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            System.out.println("======== Test =========" + response);
//            HttpMethod.POST.name();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();

        }
        Prestashop prestashop = null;
        if(response != null){
            prestashop = response.getBody();
        }else {
            prestashop = new Prestashop();
        }
        PrestashopCategory prestashopCategory = new PrestashopCategory();

        PrestashopLanguage language = new PrestashopLanguage();
        language.setId("1");
        language.setName(category.getName());

        PrestashopLanguage language2 = new PrestashopLanguage();
        language2.setId("2");
        language2.setName(category.getName());
        List<PrestashopLanguage> languages = Arrays.asList(language, language2);

        PrestashopProductName productName = new PrestashopProductName();
        productName.setLanguage(languages);

        prestashopCategory.setName(Arrays.asList(productName));

//        JSONObject json = new JSONObject();
//        json.put("value", category.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));

        PrestashopLinkRewrite linkRewrite = new PrestashopLinkRewrite();

        PrestashopLinkRewriteLanguage linkLang = new PrestashopLinkRewriteLanguage();
        linkLang.setId("1");
        linkLang.setValue(category.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));

        PrestashopLinkRewriteLanguage linkLang2 = new PrestashopLinkRewriteLanguage();
        linkLang2.setId("2");
        linkLang2.setValue(category.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));

        linkRewrite.setLanguage(Arrays.asList(linkLang, linkLang2));

        prestashopCategory.setLinkRewrite(linkRewrite);

//        prestashopCategory.setActive(category.getFlag().equals(Flag.ACTIVE) ? 1: 0);
          prestashopCategory.setActive(0);
        prestashop.setCategory(prestashopCategory);
        try {

//            JAXBContext context = JAXBContext.newInstance(Prestashop.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(prestashop, sw);

            System.out.println(sw.toString());
            HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);
            //        HttpEntity request2 = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Prestashop.class
            );
        }catch (Exception e){
            e.printStackTrace();
        }
//      response2.getBody().setCity("karachi");
//        PrestashopProductDto response = restTemplate.postForObject(url, prestashopProductDto, PrestashopProductDto.class);
        return response.getBody().getCategory();
    }

    public List<Long> getProducts() {
        String url = prestashopHost + "/api/products/?output_format=JSON";
        Object finalResponse = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
//		finalResponse = restTemplate.postForObject("", request, Object.class);
//            restTemplate.getForObject(url, Object.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(prestashopApiToken,"");
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    List.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful.");
                System.out.println(response.getBody());
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }

    public PrestashopProductDto getProduct(Integer productId) {
        String url = prestashopHost + "/api/products/" + productId;

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(prestashopApiToken,"");
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Prestashop> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful.");
                System.out.println(response.getBody());
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody().getProduct();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }

    public PrestashopProductDto addProduct(Product product) {
        String url = prestashopHost + "/api/products/";
//        String url2 = prestashopApiUrl + "/prestashop/api/products/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(prestashopApiToken,"");
        ResponseEntity<Prestashop> response = null;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpEntity request = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url + "?schema=blank",
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            System.out.println("======== Test =========" + response);
//            HttpMethod.POST.name();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();

        }
        Prestashop prestashop = null;
        if(response != null){
            prestashop = response.getBody();
        }else {
            prestashop = new Prestashop();
        }
        PrestashopProductDto prestashopProductDto = new PrestashopProductDto();
        prestashopProductDto.setPrice(product.getPrice());
        prestashopProductDto.setAvailableForOrder(1);
        prestashopProductDto.setIdTaxRulesGroup(product.getTva() != null ? product.getTva() : null);

        if(product.getCategory() != null && product.getCategory().getPrestashopCategoryId() != null){
            prestashopProductDto.setDefaultCategoryId(product.getCategory().getPrestashopCategoryId());
            AssociationsDto associationsDto = new AssociationsDto();
            PrestashopCategory category = new PrestashopCategory();
            category.setId(product.getCategory().getPrestashopCategoryId());
            associationsDto.setCategories(Arrays.asList(category));
            prestashopProductDto.setAssociations(associationsDto);
        }

//        List<PrestashopLanguage> languageIds = getLanguages();
        List<PrestashopLanguage> languages = getLanguages();
        List<PrestashopLinkRewriteLanguage> linkRewriteLanguages = new ArrayList<>();

        for (PrestashopLanguage lang : languages) {

            lang.setName(product.getName());

            PrestashopLinkRewriteLanguage linkLang = new PrestashopLinkRewriteLanguage();
            linkLang.setId(String.valueOf(lang.getId()));
            linkLang.setValue(product.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));
            linkRewriteLanguages.add(linkLang);
        }
//        PrestashopLanguage language = new PrestashopLanguage();
//        language.setId("1");
//        language.setName(product.getName());
//
//        PrestashopLanguage language2 = new PrestashopLanguage();
//        language2.setId("2");
//        language2.setName(product.getName());
//        List<PrestashopLanguage> languages = Arrays.asList(language, language2);

        PrestashopProductName productName = new PrestashopProductName();
        productName.setLanguage(languages);

        prestashopProductDto.setName(Arrays.asList(productName));


//        prestashopProductDto.setId_supplier(3);
//        prestashopProductDto.setId_manufacturer(1);
//        JSONObject json = new JSONObject();
//        json.put("value", product.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));
//        JSONObject jsonLang = new JSONObject();

        PrestashopLinkRewrite linkRewrite = new PrestashopLinkRewrite();


//        PrestashopLinkRewriteLanguage linkLang = new PrestashopLinkRewriteLanguage();
//        linkLang.setId("1");
//        linkLang.setValue(product.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));
//
//        PrestashopLinkRewriteLanguage linkLang2 = new PrestashopLinkRewriteLanguage();
//        linkLang2.setId("2");
//        linkLang2.setValue(product.getName().replaceAll(" ","_").toLowerCase(Locale.ROOT));

        linkRewrite.setLanguage(linkRewriteLanguages);

        prestashopProductDto.setLinkRewrite(linkRewrite);
        prestashopProductDto.setState(1);
        prestashopProductDto.setReference(product.getRef());
//        prestashopProductDto.setActive(product.getFlag().equals(Flag.ACTIVE) ? 1: 0);
        prestashopProductDto.setActive(0);
        prestashop.setProduct(prestashopProductDto);
        try {

//            JAXBContext context = JAXBContext.newInstance(Prestashop.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(prestashop, sw);

            System.out.println(sw.toString());
            HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);
            //        HttpEntity request2 = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Prestashop.class
            );
        }catch (Exception e){
            e.printStackTrace();
        }
//      response2.getBody().setCity("karachi");
//        PrestashopProductDto response = restTemplate.postForObject(url, prestashopProductDto, PrestashopProductDto.class);
        return response.getBody().getProduct();
    }

    public StockAvailableDto getStockAvailable(Integer stockAvailableId) {
        String url = prestashopHost + "/api/stock_availables/" + stockAvailableId + "?output_format=JSON";

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(prestashopApiToken,"");
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Prestashop> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful.");
                System.out.println(response.getBody());
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody().getStock_available();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }
    public void updateStock(List<OrderProduct> orderProducts) {
        String url = prestashopHost + "/api/stock_availables/";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(prestashopApiToken,"");
        ResponseEntity<Prestashop> response = null;

        RestTemplate restTemplate = new RestTemplate();
        try {

            HttpEntity request = new HttpEntity(headers);
            response = restTemplate.exchange(
                    url + "?schema=blank",
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            System.out.println("======== Test =========" + response);
//            HttpMethod.POST.name();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();

        }
        Prestashop prestashop = null;
        if(response != null){
            prestashop = response.getBody();
        }else {
            prestashop = new Prestashop();
        }

        for (OrderProduct orderProduct: orderProducts) {

            PrestashopProductDto prestashopProductDto = getProduct(orderProduct.getProduct().getPrestashopProductId());
            int stockAvailableId = prestashopProductDto.getAssociations().getStock_availables().get(0).getId();
            StockAvailableDto stockAvailableDto =  getStockAvailable(stockAvailableId);
//            StockAvailableDto stockAvailableDto = new StockAvailableDto();
            stockAvailableDto.setId(stockAvailableDto.getId());
            stockAvailableDto.setProductId(orderProduct.getProduct().getPrestashopProductId());
            stockAvailableDto.setProductAttributeId(0);
            stockAvailableDto.setShopId(1);
            Float incomingQty = 0f;
            for (OrderProductLocation orderProductLocation: orderProduct.getOrderProductsLocations()) {
                incomingQty = incomingQty + (( orderProductLocation.getReceivedNumberofUnits() != null ? orderProductLocation.getReceivedNumberofUnits() : orderProductLocation.getReceivedQuantity() ) - (orderProductLocation.getDamageNumberOfUnits() != null ? orderProductLocation.getDamageNumberOfUnits(): 0));
            }
            stockAvailableDto.setQuantity((stockAvailableDto.getQuantity() != null ? stockAvailableDto.getQuantity() : 0 ) + incomingQty.intValue());
        stockAvailableDto.setDependsOnStock(0);
        stockAvailableDto.setOutOfStock(0);

        prestashop.setStock_available(stockAvailableDto);
        try {

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(prestashop, sw);

            System.out.println(sw.toString());
            HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);

            response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    Prestashop.class
            );
        }catch (Exception e){
            e.printStackTrace();
        }

        }

    }
    
    public StockAvailableDto updateProductStock(List<StockUpdateDTO> stock) {

    	String url = prestashopHost + "/api/stock_availables/";
    	HttpHeaders headers = new HttpHeaders();
    	headers.setBasicAuth(prestashopApiToken,"");
    	ResponseEntity<Prestashop> response = null;

    	RestTemplate restTemplate = new RestTemplate();

    	Prestashop prestashop = new Prestashop();    	    	

    	for(StockUpdateDTO dto : stock) {

    		PrestashopProductDto prestashopProductDto = getProduct(dto.getProductId());
    		int stockAvailableId = prestashopProductDto.getAssociations().getStock_availables().get(0).getId();
    		StockAvailableDto stockAvailableDto =  getStockAvailable(stockAvailableId);        
    		stockAvailableDto.setId(stockAvailableDto.getId());
    		stockAvailableDto.setProductId(dto.getProductId());
    		stockAvailableDto.setProductAttributeId(0);
    		stockAvailableDto.setShopId(1);
    		stockAvailableDto.setQuantity(dto.getCount());
    		stockAvailableDto.setDependsOnStock(0);
    		stockAvailableDto.setOutOfStock(0);
    		prestashop.setStock_available(stockAvailableDto);    		
    	}

    	try {

    		Marshaller m = context.createMarshaller();
    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    		StringWriter sw = new StringWriter();
    		m.marshal(prestashop, sw);

    		System.out.println(sw.toString());
    		HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);

    		response = restTemplate.exchange(
    				url,
    				HttpMethod.PUT,
    				request,
    				Prestashop.class
    				);
    		return response.getBody().getStock_available();
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }

    public List<PrestashopLanguage> getLanguages() {
        String url = prestashopHost + "/api/languages/?output_format=JSON";
        Object finalResponse = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
//		finalResponse = restTemplate.postForObject("", request, Object.class);
//            restTemplate.getForObject(url, Object.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(prestashopApiToken,"");
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<Prestashop> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    Prestashop.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful.");
                System.out.println(response.getBody());
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody().getLanguages();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }
    
	public HttpStatus resetStock(List<StockCountLineItem> stockCountLineItems, Map<Long, Product> map,
			String params) {

		String url = "/api/stock_availables?filter[id_product]=[" + params + "]&display=full&output_format=XML";

		Prestashop prestashop = null;
		List<StockAvailableDto> stockAvailablelist = null;
		Map<Integer, StockAvailableDto> dtoMap = null;
		try {
			prestashop = api.getPrestaOrderDetails(url, "get");
			if (prestashop != null && prestashop.getStockAvailableList() != null) {
				stockAvailablelist = prestashop.getStockAvailableList();
				dtoMap = stockAvailablelist.stream()
						.collect(Collectors.toMap(StockAvailableDto::getProductId, Function.identity()));
			} else {
				System.out.println("Stock Available List response is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error occured while fetching the list of stock available");
		}

		List<StockAvailableDto> dtoList = new ArrayList<>();
		for (StockCountLineItem dto : stockCountLineItems) {
			if (map.containsKey(dto.getProduct().getId())) {
				Product product = map.get(dto.getProduct().getId());
				if (dtoMap.containsKey(product.getPrestashopProductId())) {
					StockAvailableDto stockDto = dtoMap.get(product.getPrestashopProductId());
					stockDto.setQuantity(dto.getQuantity());
					dtoList.add(stockDto);
				}
			}
		}
		
		/* prestashop.setStockAvailableList is set null => Because this object was also being sent with the request */
		prestashop.setStockAvailableList(null);
		prestashop.setStockAvailableListPost(stockAvailablelist);
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(prestashopApiToken, "");
		ResponseEntity<Prestashop> response = null;
		RestTemplate restTemplate = new RestTemplate();

		try {
			url = prestashopHost + "/api/stock_availables/";
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(prestashop, sw);
			System.out.println(sw.toString());
			HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);
			response = restTemplate.exchange(url, HttpMethod.PUT, request, Prestashop.class);
			return response.getStatusCode();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Stock available could not be updated");
		}
	}
}
