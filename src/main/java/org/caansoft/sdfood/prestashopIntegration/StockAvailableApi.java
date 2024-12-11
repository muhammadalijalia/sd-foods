package org.caansoft.sdfood.prestashopIntegration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@RestController
public class StockAvailableApi {

    @GetMapping("prestashop/api/stock_available")
    public Object prestashopApi() {
        String url = "http://localhost/prestashop/api/stock_availables/?output_format=JSON";
        Object finalResponse = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
//		finalResponse = restTemplate.postForObject("", request, Object.class);
//            restTemplate.getForObject(url, Object.class);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth("AXZ7MPSXVJ9JZNLX8RMKWHG44NXIK3JD","");
            HttpEntity request1 = new HttpEntity(headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request1,
                    String.class
            );
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful.");
                System.out.println(response.getBody());
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response;
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();
        }
        return null;
    }
    //Put Mapping
    @PutMapping("prestashop/api/stock_available")
    public Object createResource() {
        String url = "http://localhost/prestashop/api/stock_availables/";
        String url2 = "http://localhost/prestashop/api/stock_availables/";
        Object finalResponse = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("AXZ7MPSXVJ9JZNLX8RMKWHG44NXIK3JD","");
        ResponseEntity<Prestashop> response2 = null;

        RestTemplate restTemplate = new RestTemplate();
        try {

//			finalResponse = restTemplate.postForObject("", request, Object.class);
//            restTemplate.getForObject(url, Object.class);
            HttpEntity request2 = new HttpEntity(headers);
            response2 = restTemplate.exchange(
                    url + "&schema=blank",
                    HttpMethod.GET,
                    request2,
                    Prestashop.class
            );
            System.out.println("======== Test =========" + response2);
//            HttpMethod.POST.name();
        } catch (Exception e) {
//			logger.error("Error in saveAndGetEnedisInfo : {}", e);
            e.printStackTrace();

        }
        Prestashop prestashop = null;
        if(response2 != null){
            prestashop = response2.getBody();
        }else {
            prestashop = new Prestashop();
        }
        StockAvailableDto stockAvailableDto = new StockAvailableDto();
        stockAvailableDto.setId(78);
        stockAvailableDto.setProductId(30);
        stockAvailableDto.setProductAttributeId(0);
        stockAvailableDto.setQuantity(25);
        stockAvailableDto.setDependsOnStock(0);
        stockAvailableDto.setOutOfStock(2);
        prestashop.setStock_available(stockAvailableDto);
        try {

            JAXBContext context = JAXBContext.newInstance(Prestashop.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(prestashop, sw);

            System.out.println(sw.toString());
            HttpEntity<String> request = new HttpEntity<>(sw.toString(), headers);
            //        HttpEntity request2 = new HttpEntity(headers);
            response2 = restTemplate.exchange(
                    url2,
                    HttpMethod.PUT,
                    request,
                    Prestashop.class
            );
        }catch (Exception e){
            e.printStackTrace();
        }
//      response2.getBody().setCity("karachi");
//        PrestashopProductDto response = restTemplate.postForObject(url, prestashopProductDto, PrestashopProductDto.class);
        return response2;
    }
}