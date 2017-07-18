package jdev.server;

import jdev.dto.PointDTO;
import org.junit.Test;

/**
 * Created by srgva on 18.07.2017.
 */
public class ServerUITest {
    @Test
    public void uiTest() {
        PointDTO point = new PointDTO();
        point.setTime(System.currentTimeMillis());
        point.setLat(100L);
        point.setLon(200L);
        point.setAutoId("q987wer");

        System.out.println(point.toString());
        System.out.println(point.toJson());
    }
}
