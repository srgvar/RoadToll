package jdev.tracker;

import jdev.dto.PointDTO;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

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
        /*ArrayList<String> s = new ArrayList<String>();

        s.add("123"); s.add("456");s.add("789");

        for(String s1 : s){
            System.out.println(s1);
        }*/
        System.out.println(point.toString());
        System.out.println(point.toJson());

        //GPSService gps = new GPSService();
        //gps.getCoordinates(fileName);
        String restRequest = "http://localhost:8080/tracker?point=point123";
        URL url = new URL(restRequest);
        System.out.println(url);
        String ioString = IOUtils.toString(new URL(restRequest),"UTF8");

        System.out.println(ioString);
    }


}
