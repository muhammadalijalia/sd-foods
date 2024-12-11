package org.caansoft.sdfood.prestashopIntegration;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import org.caansoft.sdfood.prestashopIntegration.PrestashopProductDto;

@RestController
public class PrestashopProductApi {

    @GetMapping("prestashop/api/products")
    public Object prestashopApi() {
        String url = "http://localhost/prestashop/api/products/?output_format=JSON";
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
    //Post Mapping
    @PostMapping("prestashop/api/products")
    public Object createResource() {
        String url = "http://localhost/prestashop/api/products/";
        String url2 = "http://localhost/prestashop/api/products/";
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
        PrestashopProductDto prestashopProductDto = new PrestashopProductDto();
        prestashopProductDto.setPrice(90.00f);
//        prestashopProductDto.setName("x-tshirt");
        prestashopProductDto.setDefaultCategoryId(2);
        prestashopProductDto.setSupplierId(3);
        prestashopProductDto.setManufacturerId(1);
        prestashop.setProduct(prestashopProductDto);
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
                    HttpMethod.POST,
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