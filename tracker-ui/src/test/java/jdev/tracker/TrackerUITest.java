package jdev.tracker;

import jdev.dto.PointDTO;
import org.junit.Test;

/**
 * Created by srgva on 18.07.2017.
 */
public class TrackerUITest {
    @Test
    public void testTrackerUI(){
        System.out.println("Tracker UI Test....");

        PointDTO point = new PointDTO();
        point.setTime(System.currentTimeMillis());
        point.setLat(40L);
        point.setLon(140L);
        point.setAutoId("a987tui");

        System.out.println(point.toString());
        System.out.println(point.toJson());
    }

}
