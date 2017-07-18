package jdev.server;

/**
 * Created by srgva on 18.07.2017.
 */

import jdev.dto.PointDTO;
import org.junit.Test;


public class ServerTest{
    @Test
    public void testServer() {
        System.out.println("Server-core tests...");
        PointDTO point = new PointDTO();
        point.setTime(System.currentTimeMillis());
        point.setLat(10L);
        point.setLon(20L);
        point.setAutoId("q987wer");

        System.out.println(point.toString());
        System.out.println(point.toJson());

    }
}
