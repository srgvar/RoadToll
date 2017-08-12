package jdev.server;

/**
 * Created by srgva on 18.07.2017.
 */

import jdev.dto.PointDTO;
import jdev.dto.Response;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


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
        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        //restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        String url = "http://localhost:8080/tracker/";

        points.add(point);
        PointDTO point1 = new PointDTO(point.toJson());
        point1.setSpeed(2000);
        points.add(point1);

        //restTemplate.postForEntity()
        try {
            ResponseEntity<?> r = restTemplate.postForEntity(url,
                    point1, PointDTO.class);


            System.out.println("Response Entity " + r.getStatusCodeValue());
            if (r.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("On server created POINT: " + point1);
            }
            else{
                System.out.println("Error on server!!!" + r.getStatusCodeValue());
            }
        } catch(Exception e){
            System.out.println("Error" + e.getMessage());
        }
        //      PointDTO request = restTemplate.postForObject(url, point, PointDTO.class);


        //      System.out.println(request);


    }
}


