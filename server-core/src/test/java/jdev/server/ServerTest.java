package jdev.server;

/*
 * Created by srgva on 18.07.2017.
 */

import jdev.dto.PointDTO;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;


public class ServerTest {
    @Test
    public void testServer() {
        System.out.println("Server-core tests...");
        PointDTO point = new PointDTO();
        ArrayList<PointDTO> points = new ArrayList<>();
        point.setTime(System.currentTimeMillis());
        point.setLat(10L);
        point.setLon(20L);
        point.setAutoId("q987wer");
        point.setLat(50L);
        point.setLon(150L);
        point.setAutoId("z456xcv");
        point.setBearing(90);
        point.setSpeed(100);
        System.out.println(point.toString());
        System.out.println(point.toJson());


        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9090/tracker";

        points.add(point);
        PointDTO point1 = new PointDTO(point.toJson());
        point1.setSpeed(2000);
        points.add(point1);

        //restTemplate.postForEntity()
        try {
            HttpEntity<PointDTO> requestEntity = new HttpEntity<>(point1, getHeaders());

            System.out.println("ent = " + requestEntity);
            ResponseEntity<?> r = restTemplate.exchange(url, HttpMethod.POST, requestEntity, PointDTO.class);


            System.out.println("Response Entity " + r);
            if (r.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("On server created POINT: " + point1);
            }
            else{
                System.out.println("Error on server!!!" + r.getStatusCodeValue());
            }
        } catch(Exception e){
            // System.out.println(r);
            System.out.println("Error " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        //headers.add("Authorization", "test:test");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }
}


