package jdev.tracker;

import jdev.dto.PointDTO;
import org.junit.Test;

/**
 * Created by srgva on 17.07.2017.
 */
public class TrackerTest {
    @Test
    public void testTracker(){

        System.out.println("TrackerTest....");

        PointDTO point = new PointDTO();
        point.setTime(System.currentTimeMillis());
        point.setLat(50L);
        point.setLon(150L);
        point.setAutoId("z456xcv");

        System.out.println(point.toString());
        System.out.println(point.toJson());

    }
}
