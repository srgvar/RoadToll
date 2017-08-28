package jdev.tracker;

import jdev.dto.PointDTO;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by srgva on 17.07.2017.
 */

public class TrackerTest {
    String fileName = ".\\resource\\tracks\\10158.kml";
    @Test
    public void testTracker() throws IOException {

        System.out.println("TrackerTest....");

        PointDTO point = new PointDTO();
        point.setTime(System.currentTimeMillis());
        point.setLat(50L);
        point.setLon(150L);
        point.setAutoId("z456xcv");
        point.setBearing(90);
        point.setSpeed(100);
        /*ArrayList<String> s = new ArrayList<String>();

        s.add("123"); s.add("456");s.add("789");

        for(String s1 : s){
            System.out.println(s1);
        }*/
        System.out.println(point.toString());
        System.out.println(point.toJson());



        RestTemplate restTemplate = new RestTemplate();
        PointDTO point1 = new PointDTO(point.toJson());

        HttpEntity<PointDTO> entity = new HttpEntity<>(point1, getHeaders());

       try{
       ResponseEntity<?> r = restTemplate.postForEntity("http://localhost:9090/tracker", entity, HttpEntity.class);
           System.out.println(r);
} catch(Exception e){
    e.printStackTrace();
}


    }
    private static HttpHeaders getHeaders(){
        String plainCredentials="tracker:tracker";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.add("","");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        return headers;
    }


}
